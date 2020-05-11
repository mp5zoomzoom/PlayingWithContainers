FROM ubuntu AS build

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

ARG BUILD_FILE=build.xml
ARG JAR_LIB_FILE=lib/
ARG RES_FILE=Resources/
ARG SCR_FOLDER=src

WORKDIR /usr/local/app

#ADD ${JAR_LIB_FILE} lib/
COPY ${JAR_LIB_FILE}log4j-1.2.17.jar lib/
COPY ${JAR_LIB_FILE}CommInterface-1.4.jar lib/
COPY ${JAR_LIB_FILE}gson-2.6.2.jar lib/
COPY ${JAR_LIB_FILE}org.eclipse.paho.client.mqttv3_1.1.0.jar lib/
COPY ${SCR_FOLDER} src/
COPY ${RES_FILE}log4j.properties Resources/
COPY ${BUILD_FILE} .
COPY jar-in-jar-loader.zip .

RUN ant create_run_jar

########RunApp Stage

FROM java:8-jdk-alpine AS runapp

ARG RES_FILE=Resources/

WORKDIR /usr/local/app
COPY --from=build /usr/local/app/DataStringer.jar app.jar
COPY ${RES_FILE}log4j.properties Resources/

ENTRYPOINT ["java","-jar","app.jar"]

