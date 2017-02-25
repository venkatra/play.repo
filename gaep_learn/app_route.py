
# [START app]
import logging
import os
import cloudstorage as gcs
import webapp2

from flask import render_template
from flask import Flask
from daos.CBObjectsView import CBObjectsView
from google.appengine.api import app_identity
from flask.views import MethodView
from flask.views import View
from flask import send_file
import StringIO

app = Flask(__name__)

class HelloView(MethodView):
    '''
    a subclass of flask.views.MethodView
    Each HTTP method maps to a method with the same name(lowercase)
    '''
    def get(self):
        ''' just display the say hello page '''
        #self.response.write("ASDASDASD")
        #return Response("Hello", mimetype='text/csv')
        #return self.response
        strIO = StringIO.StringIO()
        strIO.write('Hello from Dan Jacob and Stephane Wirtel !')
        strIO.seek(0)
        return flask
# [START app]
import logging
import os
import cloudstorage as gcs
import webapp2

from flask import render_template
from flask import Flask
from daos.CBObjectsView import CBObjectsView
from google.appengine.api import app_identity
from flask.views import MethodView
from flask.views import View
from flask import send_file
import StringIO
from flask import Response

app = Flask(__name__)

app.add_url_rule('/data/cbobject', view_func=CBObjectsView.as_view('cbobject'))

@app.route('/')
def index():
    return render_template("/index.html")


@app.errorhandler(500)
def server_error(e):
    # Log the error and stacktrace.
    logging.exception('An error occurred during a request.')
    return 'An internal error occurred.', 500
