import tensorflow as tf
import os
import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split,KFold
from tensorflow.keras import layers
import transformers

from collections import Counter
from transformers import BertTokenizer
from keras.callbacks import ModelCheckpoint, EarlyStopping, CSVLogger

strategy = tf.distribute.MirroredStrategy()
print('Number of devices: {}'.format(strategy.num_replicas_in_sync))

traindf = pd.read_csv("lsdkDF_PP.csv",encoding= 'unicode_escape')
print(traindf.head(10))
print(traindf.indicator.value_counts())
per_vals = round(traindf["indicator"].value_counts(normalize=True,ascending = False)*100, 2)
print(per_vals)                 # ratio of humor/non-humor classes

LOAD_MODEL_PATH = "checkpoint1"         # folder containing the fine-tuned model
fineTuned = True              # check if BERT is fine-tuned
parent_dir = "dhumor/"   # path of the containing folder  
dirPath = parent_dir + LOAD_MODEL_PATH         # path to folder containing the fine-tuned model

if os.path.isdir(dirPath):
    if not os.listdir(dirPath):
        fineTuned = False           # model folder empty => model is not yet fine-tuned
else:
    print("Given directory doesn't exist")
    path = os.path.join(parent_dir, LOAD_MODEL_PATH)
    os.mkdir(path)
    print("Directory '% s' created" % LOAD_MODEL_PATH)
    fineTuned = False       # model folder not found => model is not yet fine-tuned

TARGET_COLS = ['indicator']             # target inputs for training 

MAX_LENGTH = 512            # max no. of tokens the BERT model will take
BATCH_SIZE = 12             # batch size of the BERT model
BERT_MODEL = "bert-base-uncased"
tokenizer = BertTokenizer.from_pretrained(BERT_MODEL)

class BertDataGenerator(tf.keras.utils.Sequence):               # configure model for training
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

    def __getitem__(self, idx):
        indexes = self.indexes[idx * self.batch_size : (idx + 1) * self.batch_size]
        batch_texts = self.full_texts[indexes]

        encoded = self.tokenizer.batch_encode_plus(             # define model's configuration
            batch_texts.tolist(),
            add_special_tokens = True,
            max_length = MAX_LENGTH,
            return_attention_mask = True,
            return_token_type_ids = True,
            return_tensors = "tf",              # using tensorflow
            truncation = True,
            padding = 'max_length'
        )

        input_ids = np.array(encoded["input_ids"], dtype="int32")
        attention_masks = np.array(encoded["attention_mask"], dtype="int32")
        token_type_ids = np.array(encoded["token_type_ids"], dtype="int32")

        if self.include_targets:
            labels = np.array(self.labels[indexes], dtype="float32")
            return [input_ids, attention_masks, token_type_ids], labels
        else:
            return [input_ids, attention_masks, token_type_ids]

    def on_epoch_end(self):
        if self.shuffle:
            np.random.RandomState(42).shuffle(self.indexes)

def newModel():         # in case BERT isn't fine tuned, use the original BERT model 
    input_ids = tf.keras.layers.Input(
        shape=(MAX_LENGTH,), dtype=tf.int32, name="input_ids"
    )
    
    attention_masks = tf.keras.layers.Input(
        shape=(MAX_LENGTH,), dtype=tf.int32, name="attention_masks"
    )
    
    token_type_ids = tf.keras.layers.Input(
        shape=(MAX_LENGTH,), dtype=tf.int32, name="token_type_ids"
    )
   
    bertModel = transformers.TFBertModel.from_pretrained(BERT_MODEL)
    bertModel.trainable = False                 # freeze BERT weights 

    numOutput = bertModel.bert(                 # prepare new BERT model using the numeric inputs
        input_ids, 
        attention_mask = attention_masks, 
        token_type_ids = token_type_ids
    )
    cls_output = numOutput.last_hidden_state[:, 0, :]
    binOutput = layers.Dense(1)(cls_output)                     # prediction, or binary output
    model = tf.keras.Model( inputs=[input_ids, attention_masks, token_type_ids], 
                            outputs=binOutput
                          )
    model.compile(  optimizer = tf.optimizers.Adam(learning_rate=1e-3),         # using default learning for converging to new weights
                    loss      = tf.keras.losses.BinaryCrossentropy(from_logits=True),
                    metrics   = [tf.keras.metrics.BinaryAccuracy()],
                 )
    return model

def generate_inputs(submitdf):          # generate the numeric inputs
        
        batch_texts = submitdf["text"].values.astype("str")
        #print(len(batch_texts))
        encoded = tokenizer.batch_encode_plus(
            batch_texts.tolist(),
            add_special_tokens = True,
            max_length = MAX_LENGTH,
            return_attention_mask = True,
            return_token_type_ids = True,
            return_tensors = "tf",
            truncation = True,
            padding = 'max_length'
        )

        input_ids = np.array(encoded["input_ids"], dtype="int32")
        attention_masks = np.array(encoded["attention_mask"], dtype="int32")
        token_type_ids = np.array(encoded["token_type_ids"], dtype="int32")

        return input_ids, attention_masks, token_type_ids

def KFoldTrain(model,checkpoint,k_fold,sample,epoches_per_fold):        # implementation of KFold
    fold = 1
    kfoldStats = {"fold":[],"score":[]}
    
    for train, test in k_fold.split(sample):
        trainGroup = sample.iloc[train]
        testGroup = sample.iloc[test]

        trainData = BertDataGenerator(  trainGroup["text"].values.astype("str"),            #  
                                        np.array(trainGroup[TARGET_COLS]),                  # 
                                        batch_size = BATCH_SIZE,                            # split the original
                                        shuffle = True,                                     #
                                     )                                                      # dataset into train 
                                                                                            #
        valData = BertDataGenerator(    testGroup["text"].values.astype("str"),             # and validation subsets
                                        np.array(testGroup[TARGET_COLS]),                   #
                                        batch_size = BATCH_SIZE,                            #
                                        shuffle = False,                                    #
                                   )
        
        trainScore = model.fit(     trainData,
                                    validation_data = valData,
                                    epochs = epoches_per_fold, 
                                    callbacks = checkpoint
                              )
        kfoldStats["fold"].append(fold)
        kfoldStats["score"].append(trainScore)
        fold += 1
    return kfoldStats

with strategy.scope():
    checkpoint = ModelCheckpoint(filepath = LOAD_MODEL_PATH, 
                                 monitor = 'val_binary_accuracy', 
                                 verbose = 1, 
                                 save_best_only = True, 
                                 mode = 'max'
                                 )
    early_stop = EarlyStopping(monitor='val_loss', verbose=3)
    log_csv = CSVLogger('endstat_'+LOAD_MODEL_PATH+'.csv', separator = ',', append = False)
    checkpoint_list = [checkpoint, early_stop, log_csv]
    SPLITS = 4            #number of training cycles
    k_fold_splits = KFold(n_splits = SPLITS, shuffle = True)

    if(fineTuned):         # if fine tuned, load the model from container 
        model = tf.keras.models.load_model(LOAD_MODEL_PATH)
    else:                  # if not, train the original model using initial setups
        model = newModel()
        model.summary()
        k_fol_hist = KFoldTrain( model = model,
                                checkpoint = checkpoint_list,
                                k_fold = k_fold_splits,
                                sample = traindf,
                                epoches_per_fold = 1    
                               )

    model.trainable = True                              # unfreeze BERT weights after converging
    model.compile(  optimizer=tf.optimizers.Adam(learning_rate = 1e-5),         # train again using low learning rate for optimal results
                    loss = tf.keras.losses.BinaryCrossentropy(from_logits = True),
                    metrics=[tf.keras.metrics.BinaryAccuracy()],                    
                )
    model.summary()

    k_fol_hist = KFoldTrain( model = model,
                            checkpoint = checkpoint_list,
                            k_fold = k_fold_splits,
                            sample = traindf,
                            epoches_per_fold = 1   
                        )
    

############ END OF TRAINING PHASE ############
# TESTING PHASE IS INCLUDED IN dhumor_test.py #   