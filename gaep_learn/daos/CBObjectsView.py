#[START imports]
import logging
import os
import cloudstorage as gcs
import webapp2
from google.appengine.api import app_identity
from flask.views import MethodView
from flask import stream_with_context, request, Response
from flask import send_file
from google.cloud import storage
from google.appengine.api import app_identity
from google.appengine.ext import blobstore
from google.appengine.ext.webapp import blobstore_handlers
#[END imports]

class CBObjectsView(MethodView):

    def getasd(self):
        logging.info('Reading file {0} ...'.format("ABC"))
        filepath_tostream = '/wikilink/cb_objects.txt'
        gcs_file = gcs.open(filepath_tostream)
        return send_file(gcs_file,as_attachment=False)

    def getawq(self):
        logging.info('Reading file {0} ...'.format("ABC"))
        filepath_tostream = '/wikilink/cb_objects.txt'
        #gcs_file = gcs.open(filepath_tostream)

        @stream_with_context
        def generate():
            #traverse line convert to json
            #gcs_file.seek(-1024, os.SEEK_END)
            #for ln in gcs_file.readline():
            #    yield ln
            #gcs_file.close()
            with gcs.open(filepath_tostream) as f:
                for ln in f:
                    yield f.readline()

        return Response(stream_with_context(generate()))

    def get(self):
        logging.info('Reading file {0} ...'.format("ABC"))
        filepath_tostream = '/wikilink/cb_objects.txt'
        @stream_with_context
        def generate():
            client = storage.Client()
            bucket = client.get_bucket('wikilink')
            blobstore_filename = '/gs/wikilink/cb_objects.txt'
            blob_key = blobstore.create_gs_key(blobstore_filename)
            # Instantiate a BlobReader for a given Blobstore blob_key.
            blob_reader = blobstore.BlobReader(blob_key)

            # Instantiate a BlobReader for a given Blobstore blob_key, setting the
            # buffer size to 1 MB.
            blob_reader = blobstore.BlobReader(blob_key, buffer_size=1048576)

            # Instantiate a BlobReader for a given Blobstore blob_key, setting the
            # initial read position.
            blob_reader = blobstore.BlobReader(blob_key, position=0)
            for line in blob_reader:
                yield line

        return Response(stream_with_context(generate()))

    def stream_file(self, filepath_tostream):
        try:
            logging.info('Reading file {0} ...'.format(filepath_tostream))
            gcs_file = gcs.open(filepath_tostream)
            #traverse line convert to json
            self.response.write(gcs_file.read())
            gcs_file.close()

        except Exception, e:
            logging.exception(e)
            self.delete_files()
            self.response.write('\n\nThere was an error running the demo! '
                                'Please check the logs for more details.\n')

