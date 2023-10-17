import pandas as pd
import numpy as np
import sklearn
import os
import tensorflow as tf
import tensorflow.keras.backend as K
from tensorflow import keras 
import string
from sklearn.utils import shuffle
from sklearn.model_selection import train_test_split
from sklearn.metrics import precision_score,recall_score,f1_score,accuracy_score,classification_report
from tensorflow.keras import layers
import transformers

from transformers import BertTokenizer

MODEL_TYPE = 'bert-base-uncased'
tokenizer = BertTokenizer.from_pretrained(MODEL_TYPE)
LOAD_MODEL_PATH = "checkpoint1"
LOAD_MODEL = False
TRAIN_MODEL = True
TARGET_COLS = ['indicator']
MAX_LENGTH = 512
BATCH_SIZE = 12
BERT_MODEL = "bert-base-uncased"

filepath = 'checkpoint1'            # container of the fine-tuned model
strategy = tf.distribute.MirroredStrategy()
print('Number of devices: {}'.format(strategy.num_replicas_in_sync))

#### LOADING THE DATASETS FOR EVALUATION ####

test_folder = "testDFs/"
extensions = ".csv"
version = "(" + LOAD_MODEL_PATH + ")"
testDFName1 = "eduvids_testset_PP"
testdf1 = pd.read_csv(test_folder+testDFName1+extensions)
testDFName2 = "eduvid_pred1_PP"
testdf2 = pd.read_csv(test_folder+testDFName2+extensions)
testDFName3 ="hippo_facts_DF_PP"
testdf3 = pd.read_csv(test_folder+testDFName3+extensions)
testDFName4 = "howwereopen_vihart_DF_PP"
testdf4 = pd.read_csv(test_folder+testDFName4+extensions)
testDFName5 = "DoSchoolKillsCreativity_DF_PP"
testdf5 = pd.read_csv(test_folder+testDFName5+extensions)
testDFName6 = "np_4thdim_DF_PP"
testdf6 = pd.read_csv(test_folder+testDFName6+extensions)
testDFName7 = "probosis_monkey_DF_PP"
testdf7 = pd.read_csv(test_folder+testDFName7+extensions)
testDFName8 = "ct_vid2_DF_PP"
testdf8 = pd.read_csv(test_folder+testDFName8+extensions)

##############################################

class BertDataGenerator(tf.keras.utils.Sequence):
    def __init__(
        self,
        full_texts,
        labels,
        batch_size=BATCH_SIZE,
        shuffle=True,
        include_targets=True,
    ):
        self.full_texts = full_texts
        self.labels = labels
        self.shuffle = shuffle
        self.batch_size = batch_size
        self.include_targets = include_targets
        self.tokenizer = transformers.BertTokenizer.from_pretrained(
            BERT_MODEL, do_lower_case=True
        )
        self.indexes = np.arange(len(self.full_texts))
        self.on_epoch_end()

    def __len__(self):
        return len(self.full_texts) // self.batch_size

    def on_epoch_end(self):
        if self.shuffle:
            np.random.RandomState(42).shuffle(self.indexes)

def generate_inputs(submitdf):
        
        batch_texts = submitdf["text"].values.astype("str")
        encoded = tokenizer.batch_encode_plus(
            batch_texts.tolist(),
            add_special_tokens=True,
            max_length=MAX_LENGTH,
            return_attention_mask=True,
            return_token_type_ids=True,
            return_tensors="tf",
            truncation=True,
            padding='max_length'
        )

        input_ids = np.array(encoded["input_ids"], dtype="int32")
        attention_masks = np.array(encoded["attention_mask"], dtype="int32")
        token_type_ids = np.array(encoded["token_type_ids"], dtype="int32")

        return input_ids, attention_masks, token_type_ids

def predict_and_save(model,submit_df,path_to_file):        

    inputs = generate_inputs(submit_df)     # numeric inputs for the model
    predictions = model.predict(inputs)     # an array containing values for every sentences in the dataset. the lower the value, the less likely is the sentence humorous.
    minVal = min(predictions)      # getting the lowest value from the predictions
    maxVal = max(predictions)      # getting the highest value from the predictions
                                   # minVal and maxVal forms 2 ends of the confidence range
    certainty_ratio = 0.8      # threshold of the confidence range
    certainty = (maxVal-minVal)*certainty_ratio + minVal    
    to_submit = pd.DataFrame(data={'pred': [True if val[0] >= certainty else False  for val in predictions]})# values higher than the certainty point will be classified as humor
    submit_df["pred"] = to_submit["pred"]
    submit_df["result"] = np.where((submit_df['pred'] == submit_df['humor']), 'good', 'bad')    # if prediction = true value, saved as good, else bad
    results = submit_df['result'].value_counts()
    good_preds = results['good']
    bad_preds = results['bad']
    submit_df["good"] = pd.Series(good_preds, index=submit_df.index[[0]])
    submit_df["bad"] = pd.Series(bad_preds, index=submit_df.index[[0]])
    submit_df["humor"] = submit_df["humor"].astype("bool")
    y_true = submit_df["humor"]
    y_pred = submit_df["pred"]

    print(type(y_true[0]),y_true)
    print(type(y_pred[0]),y_pred)
    print(y_true[0] == y_pred[0])

    report = classification_report(y_true, y_pred,target_names=['humor','not humor'],output_dict=True)  # calculate precision, recall, F1, and accuracy
    df = pd.DataFrame(report).transpose()
    df = df.drop("macro avg")
    df = df.drop("weighted avg")
    df = df.drop(columns="support")
    Accuracy = round(df["precision"].iloc[2],2)   # (TP+TN)/(TP+FP+FN+TN)
    Precision = round(df["precision"].iloc[0],2)  # TP/(TP+FP)
    Recall = round(df["recall"].iloc[0],2)        # TP/(TP+FN)
    F1 = round(df["f1-score"].iloc[0],2)          # 2*(Recall * Precision) / (Recall + Precision)
    print("Acc: ", Accuracy, "Pre: ", Precision, "Rec: ",Recall, "F1: ", F1)

    #### SAVING ALL THE METRICS IN THE DATASET ####

    submit_df['precision'] = pd.Series(Precision, index=submit_df.index[[0]])
    submit_df['accuracy'] = pd.Series(Accuracy, index=submit_df.index[[0]])
    submit_df['recall'] = pd.Series(Recall, index=submit_df.index[[0]])
    submit_df['F1'] = pd.Series(F1, index=submit_df.index[[0]])
    submit_df.to_csv(path_to_file,index=False)

    ###############################################

with strategy.scope():
    model = tf.keras.models.load_model(filepath)

predict_and_save(model = model,submit_df = testdf1,path_to_file = test_folder+testDFName1+version+extensions)
predict_and_save(model = model,submit_df = testdf2,path_to_file = test_folder+testDFName2+version+extensions)
predict_and_save(model = model,submit_df = testdf3,path_to_file = test_folder+testDFName3+version+extensions)
predict_and_save(model = model,submit_df = testdf4,path_to_file = test_folder+testDFName4+version+extensions)
predict_and_save(model = model,submit_df = testdf5,path_to_file = test_folder+testDFName5+version+extensions)
predict_and_save(model = model,submit_df = testdf6,path_to_file = test_folder+testDFName6+version+extensions)
predict_and_save(model = model,submit_df = testdf7,path_to_file = test_folder+testDFName7+version+extensions)
predict_and_save(model = model,submit_df = testdf8,path_to_file = test_folder+testDFName8+version+extensions)

############ END OF TESTING PHASE ############