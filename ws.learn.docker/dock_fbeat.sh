#
# Scripts to instantiate the fbeat_dkr with a specific configuration file.
#

#Variable declaration
WS_DIR=$(pwd)
LOG_VOLUME="$WS_DIR/volume_log" 
DATA_VOLUME="$WS_DIR/volume_data" 
CONFIG_VOLUME="$WS_DIR/volume_config" 
CONTAINER_NAME=fbtdk
DOCKER_IMAGE_NAME=fbeat_dkr

echo "Deleting existing instances of docker container (if present) ..."
EXISTING_CONTAINER=$(docker ps -aqf "name=$CONTAINER_NAME")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "stoping $CONTAINER_NAME..."
	docker stop $CONTAINER_NAME

	echo "deleting existing container [$EXISTING_CONTAINER] of name $CONTAINER_NAME instance ..."
	docker rm "$EXISTING_CONTAINER"

	#Deletes the registries from previous runs
	rm -rf $DATA_VOLUME/fbeats
fi

echo "Initalizing ..."
mkdir -p $LOG_VOLUME/fbeats $DATA_VOLUME/fbeats

#This file beats container gets connect to the logstash container via the link option

echo "executing docker $CONTAINER_NAME ..."
CONFIG_VOLUME_PARAM="$CONFIG_VOLUME:/fbeats"
DATA_VOLUME_PARAM="$DATA_VOLUME/fbeats:/opt/filebeat-5.1.2/data"
LOG_VOLUME_PARAM="$WS_DIR/volume_log:/watchdir" 
docker run -td -v $CONFIG_VOLUME_PARAM -v $LOG_VOLUME_PARAM -v $DATA_VOLUME_PARAM \
	--name "$CONTAINER_NAME" \
	--link lstash:lstash \
	$DOCKER_IMAGE_NAME -e -c /fbeats/filebeat.yml

echo -e "\n Docker ps...\n"
docker ps -a
