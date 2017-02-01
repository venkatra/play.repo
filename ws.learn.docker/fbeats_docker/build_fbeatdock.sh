#
# Scripts builds a docker image for filebeats. 
#

#Variable declaration
WS_DIR=$(pwd)
DOCKER_IMAGE_NAME=fbeat_dkr
FILEBEATS_DIST=filebeat-5.1.2-linux-x86_64

echo "Building docker image $DOCKER_IMAGE_NAME ..."
docker rmi -f $DOCKER_IMAGE_NAME
docker build -t $DOCKER_IMAGE_NAME -f Dockerfile .

echo -e "\n Docker images ...\n"
docker images


