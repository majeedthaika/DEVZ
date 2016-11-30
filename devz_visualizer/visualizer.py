from flask import Flask, make_response, send_from_directory, request, render_template
from flask_googlemaps import GoogleMaps
from functions import retrieveData, getGMapsMarker
from datetime import datetime
import calendar

app = Flask(__name__,template_folder="static/templates", static_url_path='')
GoogleMaps(app, key="AIzaSyAAxxcnigsHUk25BQxzhaHgZP9bsRBDQd0")

@app.route('/')
def root():
    return app.send_static_file('templates/index.html')

@app.route('/visualizer', methods=['GET', 'POST'])
def visualize():
    return app.send_static_file('templates/visualizer.html')

@app.route('/plot', methods=['GET'])
def plotter():
    lat0 = request.args.get('lat0', type=float)
    lat1 = request.args.get('lat1', type=float)
    lng0 = request.args.get('lng0', type=float)
    lng1 = request.args.get('lng1', type=float)
    day = request.args.get('day', type=int)
    hour,minute,seconds = map(int, request.args.get('time').split(":"))
    window = request.args.get('window',type=int)

    dt_obj = datetime(2015, 6, day, hour, minute, seconds)
    epoch1 = calendar.timegm(dt_obj.timetuple()) - 25200
    epoch2 = epoch1
    slideStep = request.args.get('slide', type=int)

    daystr = str(day).zfill(2)
    sql_str = """201506{daystr}""".format(daystr = daystr)

    [time, wData] = retrieveData(lat0, lat1, lng0, lng1, epoch1, epoch2, window, slideStep, table = sql_str)
    return getGMapsMarker(wData[0])
