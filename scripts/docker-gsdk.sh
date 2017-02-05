#
# Script to setup docker images of googles cloud sdk
#
# NOTE
# - https://hub.docker.com/r/google/cloud-sdk/
#

docker pull google/cloud-sdk

docker run -it --name gcloud-config google/cloud-sdk gcloud init
