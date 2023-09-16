import keras
bModel = keras.models.load_model("colbert-trained/")
bModel.summary()

import pandas as pd
import numpy as np
import sklearn
from sklearn.model_selection import GroupKFold
from tqdm import tqdm

import tensorflow as tf
from tensorflow import keras 

import os
from scipy.stats import spearmanr
from math import floor, ceil
from transformers import *

import string
import re    #for regex

from transformers import BertTokenizer

MODEL_TYPE = 'bert-base-uncased'
tokenizer = BertTokenizer.from_pretrained(MODEL_TYPE)
import nltk
nltk.download('punkt')
from nltk.tokenize import sent_tokenize
from sklearn.utils import shuffle

#rh = pd.DataFrame(pd.read_pickle('reuters_headlines.pickle'))
#prv = pd.DataFrame(pd.read_pickle('proverbs.pickle'))
#ho = pd.DataFrame(pd.read_pickle('humorous_oneliners.pickle'))
#ws = pd.read_csv('wiki_sentences.csv')
'''ws = ws.rename(columns={'0': 'text'})
ws['indicator'] = 0
ws.to_csv('wiki_sentences.csv', index=False)'''

#rh = pd.read_csv('reuters_headlines.csv')
'''rh = rh.rename(columns={'0': 'text'})
rh['indicator'] = 0
rh.to_csv('reuters_headlines.csv', index=False)'''

#ho = pd.read_csv('humorous_oneliners.csv')
'''ho = ho.rename(columns={'0': 'text'})
ho['indicator'] = 1
ho.to_csv('humorous_oneliners.csv', index=False)'''

#prv = pd.read_csv('proverbs.csv')
'''prv = prv.rename(columns={'0': 'text'})
prv['indicator'] = 0
prv.to_csv('proverbs.csv', index=False)'''

'''train = pd.read_csv('train.csv')
print(train.head())
temp = train.loc[train['humor'] == True]
print(temp.head())
for i in range(6262):
    #tmp = temp.iloc[i]['text']
    #print(temp.iloc[i]['text'])
    ho.loc[len(ho)] = {'text': temp.iloc[i]['text'], 'indicator': 1}
    #print(tmp)

ho.to_csv('humorous_oneliners.csv', index=False)
testdf = shuffle(pd.concat([ho,ws,rh,prv]))
testdf.to_csv('testdf.csv', index=False)'''
#cbrtModel = keras.models.load_model("colbert-trained/")

testdf = pd.read_csv('testdf_PP.csv')         # Dataset for testing performance of ColBERT model
print(testdf.head(10))
print("Humorous", len(testdf.loc[testdf['indicator'] == 1]))
print("Non-humorous", len(testdf.loc[testdf['indicator'] == 0]))

input_categories = list(testdf.columns[[0]])            #
output_categories = list(testdf.columns[[1]])

MAX_SENTENCE_LENGTH = 20    #  max length of a sentence (default value)
MAX_SENTENCES = 5           #  max no. of sentences in a row (default value)
MAX_LENGTH = 100            #  max length of a row (default value)
#testdf_tmp = testdf[:2000]   
print("tdft Humorous", len(testdf.loc[testdf['indicator'] == 1]))
print("tdft Non-humorous", len(testdf.loc[testdf['indicator'] == 0]))

def generate_inputs(line, truncstrat, maxLen):          # generate numeric inputs for the model

    tokenIDS = tokenizer.encode_plus(line,
        add_special_tokens=True,
        max_length=maxLen,
        truncation=True,
        truncation_strategy=truncstrat)         

    inputIDS =  tokenIDS["input_ids"]
    attentionMask = [1] * len(inputIDS)
    inputSegment = tokenIDS["token_type_ids"]
    padding_length = maxLen - len(inputIDS)
    padding_id = tokenizer.pad_token_id
    inputIDS = inputIDS + ([padding_id] * padding_length)
    attentionMask = attentionMask + ([0] * padding_length)
    inputSegment = inputSegment + ([0] * padding_length)

    return [inputIDS, attentionMask, inputSegment]


def compute_input_arrays(dataset, columns, tokenizer):         # generate overall numeric inputs based on the approach mentioned in the ColBERT paper
    model_input = []
    for xx in range((MAX_SENTENCES*3)+3):
        model_input.append([])
    
    for _, row in tqdm(dataset[columns].iterrows(), disable=False):
        i = 0
        
        # sent
        sentences = sent_tokenize(row.text)
        for k in range(MAX_SENTENCES):
            s = sentences[k] if k < len(sentences) else ''
            ids, masks, segments = generate_inputs(s, 'longest_first', MAX_SENTENCE_LENGTH)
            model_input[i].append(ids)
            i+=1
            model_input[i].append(masks)
            i+=1
            model_input[i].append(segments)
            i+=1
        
        # full row
        ids, masks, segments = generate_inputs(row.text, 'longest_first', MAX_LENGTH)
        model_input[i].append(ids)
        i+=1
        model_input[i].append(masks)
        i+=1
        model_input[i].append(segments)
        
    for xx in range((MAX_SENTENCES*3)+3):
        model_input[xx] = np.asarray(model_input[xx], dtype=np.int32)
        
    print(model_input[0].shape)
    return model_input

def print_evaluation_metrics(y_true, y_pred, label='', is_regression=True, label2=''):      
    print('==================', label2)
    ### For regression
    if is_regression:
        print('mean_absolute_error',label,':', sklearn.metrics.mean_absolute_error(y_true, y_pred))
        print('mean_squared_error',label,':', sklearn.metrics.mean_squared_error(y_true, y_pred))
        print('r2 score',label,':', sklearn.metrics.r2_score(y_true, y_pred))
        #     print('max_error',label,':', sklearn.metrics.max_error(y_true, y_pred))
        return sklearn.metrics.mean_squared_error(y_true, y_pred)
    else:
        ### FOR Classification
#         print('balanced_accuracy_score',label,':', sklearn.metrics.balanced_accuracy_score(y_true, y_pred))
#         print('average_precision_score',label,':', sklearn.metrics.average_precision_score(y_true, y_pred))
#         print('balanced_accuracy_score',label,':', sklearn.metrics.balanced_accuracy_score(y_true, y_pred))
#         print('accuracy_score',label,':', sklearn.metrics.accuracy_score(y_true, y_pred))
        print('f1_score',label,':', sklearn.metrics.f1_score(y_true, y_pred))
        
        matrix = sklearn.metrics.confusion_matrix(y_true, y_pred)
        print(matrix)
        TP,TN,FP,FN = matrix[1][1],matrix[0][0],matrix[0][1],matrix[1][0]
        Accuracy = (TP+TN)/(TP+FP+FN+TN)
        Precision = TP/(TP+FP)
        Recall = TP/(TP+FN)
        F1 = 2*(Recall * Precision) / (Recall + Precision)
        print('Acc', Accuracy, 'Prec', Precision, 'Rec', Recall, 'F1',F1)
        return sklearn.metrics.accuracy_score(y_true, y_pred)


train_input = compute_input_arrays(testdf, input_categories, tokenizer)

true_value = np.asarray(testdf[list(testdf.columns[[1]])])

predictions = bModel.predict(train_input)

print("predictions: ",np.array(predictions))
print("true value: ",np.array(true_value))
print_evaluation_metrics(np.array(true_value), np.array(predictions), '')

#### FOR MORE INFORMATION, PLEASE CHECK PAPER https://arxiv.org/abs/2004.12765 ####