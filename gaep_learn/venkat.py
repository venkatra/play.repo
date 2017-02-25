import logging
import os
import cloudstorage as gcs
import webapp2

from flask import render_template
from flask import Flask
from google.appengine.api import app_identity

app = Flask(__name__)



@app.route('/')
def index():
    return render_template("dashboard.html")