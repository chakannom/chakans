FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    SLEEP_TIME=0 \
    JAVA_OPTS=""

# Add a chakannom user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh chakannom
WORKDIR /home/chakannom

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown chakannom:chakannom entrypoint.sh
USER chakannom

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080 5701/udp

ADD *.war app.war
