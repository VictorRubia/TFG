from sklearn import metrics
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.neighbors import KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.model_selection import cross_val_score
from sklearn.metrics import confusion_matrix
import sklearn.metrics as metrics
from sklearn.metrics import plot_confusion_matrix
import pickle

trainFile = pd.read_csv('/tfg/lib/python/hrv dataset/data/final/train.csv').drop(columns=["datasetId", "pNN25", "KURT", "SKEW", "SDSD_REL_RR", "KURT_REL_RR", "SKEW_REL_RR", "VLF", "VLF_PCT", "LF", "LF_PCT", "LF_NU", "HF", "HF_PCT", "HF_NU", "TP", "LF_HF", "HF_LF", "sampen", "higuci"])
targetTrain = trainFile["condition"]
trainFile=trainFile.drop(columns=['condition'])

# knn = KNeighborsClassifier()
# modeloKnn = knn.fit(trainFile, targetTrain)

rfc = RandomForestClassifier()
modeloRfc = rfc.fit(trainFile, targetTrain)

s = pickle.dumps(modeloRfc)

with open('/tfg/lib/python/model.pickle', 'wb') as handle:
    pickle.dump(modeloRfc, handle, protocol=pickle.HIGHEST_PROTOCOL)