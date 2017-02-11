#
# Scripts to builds a docker image for gaep.
#

#Variable declaration
DOCKER_IMAGE_NAME=gaep_dev_dkr

echo "Building docker image $DOCKER_IMAGE_NAME ..."
docker rmi -f $DOCKER_IMAGE_NAME
docker build -t $DOCKER_IMAGE_NAME -f DockerFile_GAEP .

echo -e "\n Docker images ...\n"
docker images