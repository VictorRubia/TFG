import csv
import math
import pandas as pd
import sys
sys.path.insert(1, 'tfg/lib/python/')

import numpy as np
import heartpy as hp
import pandas as pd
from datetime import datetime, timedelta
import matplotlib.pyplot as plt
import urllib.request
from scipy.signal import resample
import pickle

def crearcsv(data):
    header = ['DATETIME', 'MEAN_RR', 'MEDIAN_RR', 'SDRR', 'RMSSD', 'SDSD', 'SDRR_RMSSD', 'HR', 'pNN50', 'SD1', 'SD2', 'MEAN_REL_RR', 'MEDIAN_REL_RR', 'SDRR_REL_RR', 'RMSSD_REL_RR', 'SDRR_RMSSD_REL_RR']

    with open('lib/python/probando.csv', 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)

        # write the header
        writer.writerow(header)

        # write multiple rows
        writer.writerows(data)

def procesar(data):

    with open('lib/python/model.pickle', 'rb') as handle:
        b = pickle.load(handle)

    for i in range(0,len(data)):
        fecha = data.iloc[i].DATETIME.strftime("%d/%m/%Y a las %H:%M")
        dato_estres = b.predict([data.drop(columns="DATETIME").iloc[i]])
        if(dato_estres[0] == 'no stress'):
            print(f'El {fecha} estaba <b>no estresado</b>')
        if(dato_estres[0] == 'interruption'):
            print(f'El {fecha} estaba <b>estrés leve</b>')
        if(dato_estres[0] == 'time pressure'):
            print(f'El {fecha} estaba <b>estresado</b>')

idActividad = sys.argv[1]

file = urllib.request.urlopen("http://localhost:3000/activities/"+ idActividad +"/export.csv")

df = pd.read_csv(file)

timer = df['timer']
signal = df['ppg']

#print(timer[0])

sample_rate = hp.get_samplerate_datetime(timer, timeformat='%d/%m/%Y %H:%M:%S.%f')

print('sampling rate is: %.3f Hz' % sample_rate)

# Let's run it through a standard butterworth bandpass implementation to remove everything < 0.8 and > 3.5 Hz.
filtered = hp.filter_signal(signal, [0.7, 3.5], sample_rate=sample_rate,
                            order=3, filtertype='bandpass')

resampled = resample(filtered, len(filtered) * 10)

# #don't forget to compute the new sampling rate
new_sample_rate = sample_rate * 10
# #run HeartPy over a few segments, fingers crossed, and plot results of each

numero_elementos = (int)(new_sample_rate * 60)

a_splited = [resampled[x:x + numero_elementos] for x in range(0, len(resampled), numero_elementos)]

resample_partio = np.array_split(resampled, numero_elementos)

data = []
contador = 0

print(f'START TIME {timer[0]}')
print(f'END TIME {timer[len(timer)-1]}')

for s in a_splited:
    try:
        wd, m = hp.process(s, sample_rate=new_sample_rate, report_time=False,
                           high_precision=True, clean_rr=True)
        sdsd = m['sdsd']
        rmssd = m['rmssd']
        hr = m['bpm']
        pnn50 = m['pnn50']
        sd1 = m['sd1']
        sd2 = m['sd2']
        mean_rr = m['ibi']
        median_rr = np.median(m['ibi'])
        sdnn = m['sdnn']
        sdnn_rmssd = sdnn/rmssd

        if not math.isnan(sdsd) and not math.isnan(rmssd) and not math.isnan(hr) and not math.isnan(hr) and not math.isnan(pnn50) and not math.isnan(sd1) and not math.isnan(sd2) and not math.isnan(mean_rr) and not math.isnan(mean_rr) and not math.isnan(median_rr) and not math.isnan(sdnn) and not math.isnan(sdnn_rmssd):
            data.append([datetime.strptime(timer[0], "%d/%m/%Y %H:%M:%S.%f") + timedelta(minutes=1*contador) ,mean_rr, median_rr, sdnn, rmssd, sdsd, sdnn_rmssd, hr, pnn50, sd1, sd2, m['MEAN_REL_RR'], m['MEDIAN_REL_RR'], m['SDRR_REL_RR'], m['RMSSD_REL_RR'], m['SDRR_RMSSD_REL_RR']])

#         for measure in m.keys():
#             print('%s: %f' % (measure, m[measure]))
        contador = contador + 1
    except Exception as e:
        print(f'El {(datetime.strptime(timer[0], "%d/%m/%Y %H:%M:%S.%f") + timedelta(minutes=1*contador)).strftime("%d/%m/%Y a las %H:%M")} <b>no ha sido posible medir</b>')

crearcsv(data)

header = ['DATETIME', 'MEAN_RR', 'MEDIAN_RR', 'SDRR', 'RMSSD', 'SDSD', 'SDRR_RMSSD', 'HR', 'pNN50', 'SD1', 'SD2', 'MEAN_REL_RR', 'MEDIAN_REL_RR', 'SDRR_REL_RR', 'RMSSD_REL_RR', 'SDRR_RMSSD_REL_RR']

datos_a_procesar = pd.DataFrame(data, columns = header)

procesar(datos_a_procesar)