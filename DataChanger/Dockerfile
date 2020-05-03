FROM java:8-jdk-alpine

#!!!!!!  Uncomment this section for ubuntu base image and comment out the alpine
#FROM ubuntu

# Install OpenJDK-8
#RUN apt-get update && \
#    apt-get install -y openjdk-8-jdk && \
#    apt-get install -y ant && \
#    apt-get clean;

# Fix certificate issues
#RUN apt-get update && \
#    apt-get install ca-certificates-java && \
#    apt-get clean && \
#    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
#ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
#RUN export JAVA_HOME

#!!!!!!! End uncomment for ubuntu


ARG JAR_FILE=bin/DataChanger.jar
ARG JAR_LIB_FILE=lib/
ARG RES_FILE=Resources/

WORKDIR /usr/local/app

COPY ${JAR_FILE} app.jar

#ADD ${JAR_LIB_FILE} lib/
COPY ${JAR_LIB_FILE}log4j-1.2.17.jar lib/
COPY ${JAR_LIB_FILE}CommInterface-1.3.jar lib/
COPY ${JAR_LIB_FILE}gson-2.6.2.jar lib/
COPY ${JAR_LIB_FILE}org.eclipse.paho.client.mqttv3_1.1.0.jar lib/
COPY ${RES_FILE}log4j.properties Resources/

ENTRYPOINT ["java","-Djava.ext.dirs=lib","-jar","app.jar"]


