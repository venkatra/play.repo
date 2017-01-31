#
# Scripts builds a docker image for filebeats. And then instantiates the docker
# instance with a specific configuration file.
#

#Variable declaration
WS_DIR=$(pwd)
CONTAINER_NAME=fbtdk
DOCKER_IMAGE_NAME=testdock
FILEBEATS_DIST=filebeat-5.1.2-linux-x86_64

echo "Deleting existing instances of docker container (if present) ..."
EXISTING_CONTAINER=$(docker ps -aqf "name=$CONTAINER_NAME")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "stoping $CONTAINER_NAME..."
	docker stop $CONTAINER_NAME

	echo "deleting existing container [$EXISTING_CONTAINER] of name $CONTAINER_NAME instance ..."
	docker rm "$EXISTING_CONTAINER"
fi

echo "Building docker image $DOCKER_IMAGE_NAME ..."
docker rmi -f $DOCKER_IMAGE_NAME
docker build -t $DOCKER_IMAGE_NAME -f Dockerfile .

echo "executing docker $CONTAINER_NAME ..."
CONFIG_FILE_VOLUME="$WS_DIR/filebeat.yml:/fbeats/filebeat.yml"
LOG_VOLUME="$WS_DIR/../volume_log:/watchdir" 
docker run -td -v $CONFIG_FILE_VOLUME -v $LOG_VOLUME \
	--name "$CONTAINER_NAME" \
	$DOCKER_IMAGE_NAME -e -c /fbeats/filebeat.yml

echo -e "\n Docker ps...\n"
docker ps -a


