#!/bin/bash

#################################
#
# script to setup environment variables.
#
#################################
WS_DIR=$(pwd)

alias gcsdk='docker run --rm -ti --volumes-from gcloud-config -v ${WS_DIR}:/gcp_learn google/cloud-sdk'

MAVEN_HOME=/Users/d3vl0p3r/Dev/lib/apache-maven-3.3.1/

export PATH=$PATH:${MAVEN_HOME}/bin