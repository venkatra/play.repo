"""A sample app that operates on GCS files with blobstore API."""

import cloudstorage
from google.appengine.api import app_identity
from google.appengine.ext import blobstore
from google.appengine.ext.webapp import blobstore_handlers
import webapp2
import logging
import os
import cloudstorage as gcs
import webapp2
from flask import render_template
from flask import Flask

class dummy(webapp2.RequestHandler):
    def get(self):
        filepath_tostream = '/wikilink/cb_objects.txt'
        self.response.headers['Content-Type'] = 'text/plain'
        stat = gcs.stat(filepath_tostream)
        def read_in_chunks(file_object, chunk_size=256):
            """Lazy function (generator) to read a file piece by piece.
            Default chunk size: 1k."""
            while True:
                data = file_object.read(chunk_size)
                if not data:
                    break
                self.response.write(data)

        f = gcs.open(filepath_tostream)
        for piece in read_in_chunks(f):
            pass

# This handler creates a file in Cloud Storage using the cloudstorage
# client library and then serves the file back using the Blobstore API.
class CreateAndServeFileHandler(blobstore_handlers.BlobstoreDownloadHandler):

    def get(self):
        # Get the default Cloud Storage Bucket name and create a file name for
        # the object in Cloud Storage.
        bucket = app_identity.get_default_gcs_bucket_name()

        # Cloud Storage file names are in the format /bucket/object.
        filename = '/wikilink/cb_objects.txt'.format(bucket)

        # In order to read the contents of the file using the Blobstore API,
        # you must create a blob_key from the Cloud Storage file name.
        # Blobstore expects the filename to be in the format of:
        # /gs/bucket/object
        blobstore_filename = '/gs{}'.format(filename)
        blob_key = blobstore.create_gs_key(blobstore_filename)

        # BlobstoreDownloadHandler serves the file from Google Cloud Storage to
        # your computer using blob_key.
        self.send_blob(blob_key)


app = webapp2.WSGIApplication([
    ('/', dummy),
    ('/data/cbobject', CreateAndServeFileHandler)], debug=True)
