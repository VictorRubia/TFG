from sklearn.neighbors import KNeighborsClassifier
import pandas as pd
import pickle

trainFile = pd.read_csv('lib/python/hrv dataset/data/final/train.csv').drop(columns=["datasetId", "pNN25", "KURT", "SKEW", "SDSD_REL_RR", "KURT_REL_RR", "SKEW_REL_RR", "VLF", "VLF_PCT", "LF", "LF_PCT", "LF_NU", "HF", "HF_PCT", "HF_NU", "TP", "LF_HF", "HF_LF", "sampen", "higuci"])

X_train = trainFile.drop(columns='condition')
y_train = trainFile['condition']

knn = KNeighborsClassifier()
knn.fit(X_train, y_train)

s = pickle.dumps(knn)

with open('lib/python/model.pickle', 'wb') as handle:
    pickle.dump(knn, handle, protocol=pickle.HIGHEST_PROTOCOL)