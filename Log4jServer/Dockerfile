FROM java:8-jdk-alpine

VOLUME /usr/local/app/log
ARG JAR_FILE=lib/log4j-1.2.17.jar
ARG RES_FILE=Resources/

WORKDIR /usr/local/app

COPY ${JAR_FILE} app.jar

COPY ${RES_FILE}log4j.properties Resources/

ENTRYPOINT ["java","-classpath","app.jar","org.apache.log4j.net.SimpleSocketServer","4712","./Resources/log4j.properties"]


