#!/bin/bash

#################################
#
# script to setup environment variables.
#
#################################
WS_DIR=$(pwd)

alias gcsdk='docker run --rm -ti --volumes-from gcloud-config -v ${WS_DIR}:/gcp_learn google/cloud-sdk'
