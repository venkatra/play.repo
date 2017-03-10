#!/usr/bin/env bash

####################################################
#
#	Script to create and execute the dataflow job. This will extract various data from the warc file of common crawl
#   dataset.
#
#    Notes:
#       - script to be executed from parent module folder
#
####################################################

. ./scripts/setenv.sh

export GOOGLE_APPLICATION_CREDENTIALS=/Users/d3vl0p3r/Dev/git_play_repo/ssh_keys/get2know-gcp-2017-1001133a0036.json

echo "Executing warc file data reader ..."
java -cp dataflow/target/dataflow-bundled-1.0-SNAPSHOT.jar \
ca.effacious.learn.dflow.WARCDatafileReader \
    --runner=BlockingDataflowPipelineRunner \
    --project=get2know-gcp-2017 \
    --stagingLocation=gs://bkt-crawl-app/app_artifact \
    --inputFile=gs://bkt-common-crawl-201701/segments/1484*/warc/*.gz \
    --output=gs://bkt-crawl-app/etl_dflow/warc_data_2


# gs://bkt-crawl-app/stage_v1/raw_unzip/*