# Copyright 2016 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# [START app]
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
    return render_template("index.html")

def get(self):
    bucket_name = os.environ.get('BUCKET_NAME',
                                 app_identity.get_default_gcs_bucket_name())

    self.response.headers['Content-Type'] = 'text/plain'
    self.response.write('Demo GCS Application running from Version: '
                        + os.environ['CURRENT_VERSION_ID'] + '\n')
    self.response.write('Using bucket name: ' + bucket_name + '\n\n')

def read_file(self, filename):
    self.response.write('Abbreviated file content (first line and last 1K):\n')

    gcs_file = gcs.open(filename)
    self.response.write(gcs_file.readline())
    gcs_file.seek(-1024, os.SEEK_END)
    self.response.write(gcs_file.read())
    gcs_file.close()

def list_bucket(self, bucket):
    """Create several files and paginate through them.

    Production apps should set page_size to a practical value.

    Args:
      bucket: bucket.
    """
    self.response.write('Listbucket result:\n')

    page_size = 1
    stats = gcs.listbucket(bucket + '/foo', max_keys=page_size)
    while True:
        count = 0
        for stat in stats:
            count += 1
            self.response.write(repr(stat))
            self.response.write('\n')

        if count != page_size or count == 0:
            break
        stats = gcs.listbucket(bucket + '/foo', max_keys=page_size,
                               marker=stat.filename)

@app.errorhandler(500)
def server_error(e):
    # Log the error and stacktrace.
    logging.exception('An error occurred during a request.')
    return 'An internal error occurred.', 500
# [END app]
