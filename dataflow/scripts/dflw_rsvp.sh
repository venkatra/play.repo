#!/usr/bin/env bash

####################################################
#
#	Script to create and execute the dataflow job. This will process the rsvp event by subscribing to a topic.
#
#    Notes:
#       - script to be executed from parent module folder
#
####################################################

. ./scripts/setenv.sh

export GOOGLE_APPLICATION_CREDENTIALS=/Users/d3vl0p3r/Dev/git_play_repo/ssh_keys/get2know-gcp-2017-1001133a0036.json

echo "Executing rsvp subscriber ..."
java -cp dataflow/target/dataflow-bundled-1.0-SNAPSHOT.jar \
ca.effacious.learn.dflow.meetup.dflow.RSVPDflowAdapter \
    --runner=BlockingDataflowPipelineRunner \
    --project=get2know-gcp-2017 \
    --stagingLocation=gs://bkt-crawl-app/app_artifact \
    --topicSubscription=projects/get2know-gcp-2017/subscriptions/dflow_dboard \
    --streaming \
    --zone=us-east1-b \
    --numWorkers=3 --maxNumWorkers=5 \
    --diskSizeGb=20 --workerMachineType=n1-standard-2
