#!/usr/bin/env bash

####################################################
#
#	Script to submit the spark job in gcp cloud
#    Notes:
#       - script to be executed from parent module folder
#
####################################################

. ./scripts/setenv.sh

CLUSTER_NAME="ccrawl-learn-cluster-2017"

gcsdk gsutil rm -r -f gs://bkt-crawl-app/stage/raw_unzip

gcsdk gcloud dataproc jobs submit spark --cluster ${CLUSTER_NAME} \
      --jars ${DOCKER_HOSTED_DIR}/spark_dataproc/target/spark_dataproc-1.0-SNAPSHOT-jar-with-dependencies.jar \
      --files=${DOCKER_HOSTED_DIR}/spark_dataproc/src/main/resources/log4j.properties \
      --class ca.effacious.learn.gcp.spark.commoncrawl.CommonCrawlAppUnzipMain \
        -appEnvConf gs://bkt_app_artifact/CommonCrawlAppUnzip.conf \
        -spark_master none \
        -input_file_path gs://bkt-common-crawl-201701/segments/1484560279169.4/warc/*.gz \
        -output_file_path gs://bkt-crawl-app/stage/raw_unzip

#gs://bkt-common-crawl-201701/segments/1484560279169.4/warc/