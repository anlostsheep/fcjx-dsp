FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ENV JAVA_OPTS="-Xms3072m -Xmx3072m"
ENV SPRINGBOOT_PROPERTIES="--logging.file.path=/opt/app/fcjx-dsp/logs"
WORKDIR /opt/app/fcjx-dsp/bin
COPY target/$JAR_FILE app.jar
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -jar app.jar $SPRINGBOOT_PROPERTIES"]
