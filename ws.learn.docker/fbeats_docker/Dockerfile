#
#	This docker file is for creating filebeats docker images. It downloads the filebeats
# linux tarball and installs the same under /opt/fbeats folder.
#

FROM alpine

# Build variables
ENV FILEBEAT_VERSION=5.1.2

# Beats doc : 
#	https://www.elastic.co/guide/en/beats/filebeat/current/filebeat-installation.html

#download curl, Download and install filebeats
RUN set -x && \
	echo "FILEBEAT VERSION : ${FILEBEAT_VERSION}" && \
	FILEBEAT_URL="https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-${FILEBEAT_VERSION}-linux-x86_64.tar.gz" && \
	FILEBEAT_HOME="/opt/filebeat-${FILEBEAT_VERSION}" && \
	apk --no-cache add curl && \
	mkdir -p /opt && \
	cd /opt && \
	curl ${FILEBEAT_URL} | tar xz -C . && \
	mv filebeat*linux* ${FILEBEAT_HOME} && \
	ls -l && \
	apk del curl

VOLUME	[ "/watchdir" ]
ENTRYPOINT [ "/opt/filebeat-5.1.2/filebeat" ]
#CMD [ "-c","/opt/filebeat-5.1.2/filebeat.yml", "-e" ]