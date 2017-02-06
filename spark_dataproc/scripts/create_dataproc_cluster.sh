#!/usr/bin/env bash

####################################################
#
#	Script to create a dataproc cluster
#       - https://cloud.google.com/dataproc/docs/guides/create-cluster
#    Notes:
#       - script to be executed from parent module folder
#
####################################################

. ./scripts/setenv.sh

alias gcsdk='docker run --rm -ti --volumes-from gcloud-config -v ${WS_DIR}:/gcp_learn google/cloud-sdk'

CLUSTER_NAME="wikilink-learn-cluster-2017"

echo "Creating cluster ${CLUSTER_NAME} ..."
gcsdk gcloud dataproc clusters create ${CLUSTER_NAME} \
    --zone us-east1-b --master-machine-type n1-standard-2 --master-boot-disk-size 50 \
    --num-workers 2 --worker-machine-type n1-standard-1 --worker-boot-disk-size 50 \
    --image-version 1.1 --project ${GCP_PROJECT}

echo "Staging bucket information ...."
gcsdk gcloud dataproc clusters describe ${CLUSTER_NAME}
