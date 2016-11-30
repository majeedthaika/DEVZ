import StringIO
import urllib
from matplotlib.backends.backend_agg import FigureCanvasAgg as FigureCanvas
from flask import make_response, Markup
import json

from sqlalchemy import create_engine
import pandas as pd
import pymysql
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import math
import calendar
from datetime import datetime
from sklearn.decomposition import PCA
from sklearn import preprocessing
from scipy.cluster.hierarchy import linkage, fcluster, dendrogram
from sklearn.metrics import mean_squared_error

pymysql.install_as_MySQLdb()
engine = create_engine('mysql+mysqldb://root@localhost/ttet')

def avgCars(raw):
    if(len(raw.index) == 0):
        wData = raw[['imei', 'lat', 'lng', 'speed', 'direction']].groupby('imei').sum()
    else:
        wData = raw[['imei', 'lat', 'lng', 'speed', 'direction']].groupby('imei').mean()

    wData['direction'] = wData['direction'] / 180 * math.pi
    return wData

def retrieveData(lat0, lat1, lng0, lng1, epoch1, epoch2, window, slideStep, table=""):
    time = []
    wData = []

    sql_str = """
    SELECT * FROM `{table}`
    WHERE {lat0} < lat AND lat < {lat1}
    AND {lng0} < lng AND lng < {lng1}
    AND {epoch1} <= ts AND ts <= {epoch2}""".format(
        table=table, lat0=lat0, lat1=lat1, lng0=lng0, lng1=lng1,
        epoch1=epoch1-window/2, epoch2=epoch2+window/2
    )

    raw = pd.read_sql_query(sql_str, engine)
    raw = raw.sort_values('ts')

    start = 0
    end = 0

    ts = epoch1;
    while ts <= epoch2:

        while (end < len(raw.index) and raw['ts'][end] <= ts + window/2):
            end += 1
        while (raw['ts'][start] < ts - window/2):
            start += 1

        wData.append(avgCars(raw[start:end]))
        time.append(ts)

        ts += slideStep

    return [time, wData]

def getGMapsMarker(wData):
    X = wData['lng'].values
    Y = wData['lat'].values
    sp = wData['speed'].values
    R = wData['direction'].values*180/np.pi

    markers = []
    for i in range(len(wData)):
        markers.append(json.dumps({
            'lat': Y[i],
            'lng': X[i],
            'speed': sp[i],
            'rotation': R[i]
        }))
    return json.dumps(markers)
