WS_DIR=$(pwd)
CONTAINER_NAME=fbtdk
DOCKER_IMAGE_NAME=testdock
FILEBEATS_DIST=filebeat-5.1.2-linux-x86_64

# https://www.elastic.co/guide/en/beats/filebeat/current/filebeat-installation.html

echo "Downloading filebeats ${FILEBEATS_DIST} ...."
curl -L -O "https://artifacts.elastic.co/downloads/beats/filebeat/${FILEBEATS_DIST}.tar.gz"
tar xzvf "${FILEBEATS_DIST}.tar.gz"
mv $FILEBEATS_DIST fbeats

echo "Building docker image $DOCKER_IMAGE_NAME ..."
docker rmi -f $DOCKER_IMAGE_NAME
docker build -t $DOCKER_IMAGE_NAME -f Dockerfile .

echo "executing docker $CONTAINER_NAME ..."
EXISTING_CONTAINER=$(docker ps -aqf "name=$CONTAINER_NAME")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "stoping $CONTAINER_NAME..."
	docker stop $CONTAINER_NAME

	echo "deleting existing container [$EXISTING_CONTAINER] of name $CONTAINER_NAME instance ..."
	docker rm "$EXISTING_CONTAINER"
fi


CONFIG_FILE_VOLUME="$WS_DIR/filebeat.yml:/fbeats/filebeat.yml"
LOG_VOLUME="$WS_DIR/../volume_log:/watchdir" 
docker run -td -v $CONFIG_FILE_VOLUME -v $LOG_VOLUME \
	--name "$CONTAINER_NAME" \
	$DOCKER_IMAGE_NAME 
	#-configtest -c /fbeats/filebeat.yml

	#/bin/ash

echo -e "\n Docker ps...\n"
docker ps


