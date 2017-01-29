FROM busybox

#RUN apk --no-cache add curl
RUN mkdir /fbeats
ADD ./fbeats /fbeats
VOLUME	[ "/watchdir" ]

#WORKDIR /fbeats

ENTRYPOINT [ "/fbeats/filebeat" ]
CMD [ "-c","/fbeats/filebeat.yml", "-e" ]