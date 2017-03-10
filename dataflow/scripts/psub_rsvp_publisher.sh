#!/usr/bin/env bash

####################################################
#
#	Script to publish meetup rsvp events to cloud pub-sub topic.
#
#    Notes:
#       - script to be executed from parent module folder
#
####################################################

. ./scripts/setenv.sh

export GOOGLE_APPLICATION_CREDENTIALS=/Users/d3vl0p3r/Dev/git_play_repo/ssh_keys/get2know-gcp-2017-1001133a0036.json
#export GOOGLE_APPLICATION_CREDENTIALS=/Users/d3vl0p3r/.config/gcloud/application_default_credentials.json

echo "Executing rsvp publisher ..."
java -cp dataflow/target/dataflow-bundled-1.0-SNAPSHOT.jar \
ca.effacious.learn.dflow.meetup.RSVPRestPublisher