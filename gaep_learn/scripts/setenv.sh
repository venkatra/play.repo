#!/bin/bash

#################################
#
# script to setup environment variables.
#
#################################
WS_DIR=/Users/d3vl0p3r/Dev/git_play_repo/gcp_learn/
DOCKER_IMAGE_NAME=gaep_dev_dkr

alias gaep_dkr='docker run --rm -ti --volumes-from gcloud-config -v ${WS_DIR}:/gcp_learn -P $DOCKER_IMAGE_NAME /bin/bash'
shopt -s expand_aliases

MAVEN_HOME=/Users/d3vl0p3r/Dev/lib/apache-maven-3.3.1/

export PATH=$PATH:${MAVEN_HOME}/bin

export GCP_PROJECT="get2know-gcp-2017"
export DOCKER_HOSTED_DIR="/gcp_learn"
export GCP_CODE_BUCKET="gs://bkt_app_artifact"