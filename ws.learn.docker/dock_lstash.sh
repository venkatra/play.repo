
#
#	Script to instantiate logstash docker
#

WS_DIR=$(pwd)
CONFIG_FILE_VOLUME="$WS_DIR/volume_config"
LOG_VOLUME="$WS_DIR/volume_log" 
DATA_VOLUME="$WS_DIR/volume_data" 
LOGSTASH_DOCKER_CONTAINER=lstash
FILEBEAT_DOCKER_CONTAINER=fbtdk

echo "Deleting existing instances of docker container (if present) ..."
EXISTING_CONTAINER=$(docker ps -aqf "name=$LOGSTASH_DOCKER_CONTAINER")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "stoping $LOGSTASH_DOCKER_CONTAINER..."
	docker stop $LOGSTASH_DOCKER_CONTAINER

	echo "deleting existing container [$EXISTING_CONTAINER] of name $LOGSTASH_DOCKER_CONTAINER instance ..."
	docker rm "$EXISTING_CONTAINER"

	#Deletes the registries from previous runs
	rm -rf $LOG_VOLUME/lstash
	rm -rf $DATA_VOLUME/lstash
fi

echo "Initalizing ..."
mkdir -p $LOG_VOLUME/lstash $DATA_VOLUME/lstash

echo "Running $LOGSTASH_DOCKER_CONTAINER docker ...."
CONFIG_FILE_VOLUME_PARAM="$CONFIG_FILE_VOLUME:/config-dir"
LOG_VOLUME_PARAM="$LOG_VOLUME/lstash:/var/log/logstash"
DATA_VOLUME_PARAM="$DATA_VOLUME/lstash:/usr/share/logstash/data" 

docker run  -td --name "$LOGSTASH_DOCKER_CONTAINER" \
			-v "$CONFIG_FILE_VOLUME_PARAM" \
			-v "$LOG_VOLUME_PARAM" \
			-v "$DATA_VOLUME_PARAM" \
			--link elastic:elasticsearch \
			logstash \
			  -f /config-dir/logstash_fbeats_to_elastic.conf 
			  # --config.test_and_exit

