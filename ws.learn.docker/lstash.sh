#
#	This script was used as part of exploring logstash
#

WS_DIR=$(pwd)
CONTAINER_NAME=lstash
DOCKER_IMAGE=logstash

EXISTING_CONTAINER=$(docker ps -aqf "name=$CONTAINER_NAME")
if [ ! -z "$EXISTING_CONTAINER" ]; then
	echo "deleting existing container [$EXISTING_CONTAINER] of name $CONTAINER_NAME instance ..."
	docker rm "$EXISTING_CONTAINER"
fi

DATA_VOLUME="$WS_DIR/volume_data/n4jdb:/data" 
docker run -d -v $DATA_VOLUME \
	--name "$CONTAINER_NAME" \
	$DOCKER_IMAGE

echo -e "\n Docker ps...\n"
docker ps