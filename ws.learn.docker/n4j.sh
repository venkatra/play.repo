
WS_DIR=/Users/d3vl0p3r/Dev/workspace/ws.learn.docker
CONTAINER_NAME=n4j

EXISTING_CONTAINER=$(docker ps -aqf "name=$CONTAINER_NAME")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "stoping $CONTAINER_NAME..."
	docker stop $CONTAINER_NAME

	echo "deleting existing container [$EXISTING_CONTAINER] of name $CONTAINER_NAME instance ..."
	docker rm "$EXISTING_CONTAINER"
fi

DATA_VOLUME="$WS_DIR/volume_data/n4jdb:/data" 
LOG_VOLUME="$WS_DIR/volume_log/n4j:/var/lib/neo4j/logs" 
docker run -d -v $DATA_VOLUME -v $LOG_VOLUME \
	--name "$CONTAINER_NAME" \
	neo4j

echo -e "\n Docker ps...\n"
docker ps
