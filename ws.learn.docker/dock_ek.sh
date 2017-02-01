
#
#	Script to instantiate elastic and kibana containers
#

WS_DIR=$(pwd)
CONFIG_FILE_VOLUME="$WS_DIR/volume_config"
LOG_VOLUME="$WS_DIR/volume_log" 
DATA_VOLUME="$WS_DIR/volume_data" 
ELASTIC_DOCKER_CONTAINER=elastic
KIBANA_DOCKER_CONTAINER=kibana

echo "Deleting existing instances of docker container (if present) ..."
EXISTING_CONTAINER=$(docker ps -aqf "name=$ELASTIC_DOCKER_CONTAINER")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "stoping $ELASTIC_DOCKER_CONTAINER..."
	docker stop $ELASTIC_DOCKER_CONTAINER $KIBANA_DOCKER_CONTAINER

	echo "deleting existing container [$EXISTING_CONTAINER] of name $ELASTIC_DOCKER_CONTAINER instance ..."
	docker rm "$ELASTIC_DOCKER_CONTAINER" $KIBANA_DOCKER_CONTAINER

	#Deletes the registries from previous runs
	rm -rf $LOG_VOLUME/eskib
	rm -rf $DATA_VOLUME/eskib
fi

echo "Initalizing ..."
mkdir -p $LOG_VOLUME/eskib $DATA_VOLUME/eskib

echo "Running $ELASTIC_DOCKER_CONTAINER docker ...."
CONFIG_FILE_VOLUME_PARAM="$CONFIG_FILE_VOLUME:/config-dir"
LOG_VOLUME_PARAM="$LOG_VOLUME/eskib:/var/log/logstash"
DATA_VOLUME_PARAM="$DATA_VOLUME/eskib:/usr/share/logstash/data" 

docker run  -td --name "$ELASTIC_DOCKER_CONTAINER" \
			-v "$CONFIG_FILE_VOLUME_PARAM" \
			-v "$LOG_VOLUME_PARAM" \
			-v "$DATA_VOLUME_PARAM" \
			-p 9200:9200 \
			elasticsearch

docker run  -td --name "$KIBANA_DOCKER_CONTAINER" \
			-v "$CONFIG_FILE_VOLUME_PARAM" \
			-v "$LOG_VOLUME_PARAM" \
			-v "$DATA_VOLUME_PARAM" \
			--link $ELASTIC_DOCKER_CONTAINER:elasticsearch \
			-p 5601:5601 \
			kibana

