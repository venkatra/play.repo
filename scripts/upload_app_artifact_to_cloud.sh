#!/usr/bin/env bash

####################################################
#
#	Script to upload the application artifact to "bkt_app_artifact" @ cloud storage
#
####################################################

. ./scripts/setenv.sh

echo "Uploading spark_dataproc artifact ..."

#delete any existing file
gcsdk gsutil rm ${GCP_CODE_BUCKET}/spark_dataproc-1.0-SNAPSHOT-jar-with-dependencies.jar

gcsdk gsutil cp ${DOCKER_HOSTED_DIR}/spark_dataproc/target/spark_dataproc-1.0-SNAPSHOT-jar-with-dependencies.jar ${GCP_CODE_BUCKET}
