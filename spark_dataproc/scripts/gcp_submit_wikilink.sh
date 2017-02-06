#!/usr/bin/env bash

####################################################
#
#	Script to submit the spark job in gcp cloud
#    Notes:
#       - script to be executed from parent module folder
#
####################################################

. ./scripts/setenv.sh

CLUSTER_NAME="wikilink-learn-cluster-2017"

gcsdk gcloud dataproc jobs submit spark --cluster ${CLUSTER_NAME} \
      --jars ${DOCKER_HOSTED_DIR}/spark_dataproc/target/spark_dataproc-1.0-SNAPSHOT-jar-with-dependencies.jar \
      --files=${DOCKER_HOSTED_DIR}/spark_dataproc/src/main/resources/log4j.properties \
      --class ca.effacious.learn.gcp.spark.WikiLinkAppMain \
        -appEnvConf gs://bkt_app_artifact/WikiLinkApp.conf \
        -spark_master none \
        -input_file_path gs://wikilink/wiki_links.txt
