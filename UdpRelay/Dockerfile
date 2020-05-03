FROM java:8-jdk-alpine

ARG JAR_FILE=bin/UdpRelay.jar

WORKDIR /usr/local/app

COPY ${JAR_FILE} app.jar

EXPOSE 9876/udp

ENTRYPOINT ["java","-jar","app.jar"]


