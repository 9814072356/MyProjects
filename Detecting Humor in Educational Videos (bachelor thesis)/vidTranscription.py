import whisper
import pandas as pd
from nltk.tokenize import sent_tokenize
from tqdm import tqdm
from googletrans import Translator, constants
from pprint import pprint

vidname = "DoSchoolKillsCreativity"
extension = ".mp4"
dfName = vidname + "_DF" + ".csv"
isEng = True
model = whisper.load_model("base")
result = model.transcribe("vids/"+vidname + extension, verbose = False, language="en")
sentences = sent_tokenize(result["text"])
if not isEng:
    sentences_translate = []
    translator = Translator()
    translation = translator.translate(sentences, src='de')
    for s in translation:
        
        print(f"{s.origin} ({s.src}) --> {s.text} ({s.dest})")
        sentences_translate.append(s.text)
        df = pd.DataFrame(sentences_translate, columns=['text'])
        df.to_csv(dfName,index = False)
else:
    df = pd.DataFrame(sentences, columns=['text'])
    df.to_csv("testDFS/"+dfName,index = False)


#print(sentences)
#print(result["text"])