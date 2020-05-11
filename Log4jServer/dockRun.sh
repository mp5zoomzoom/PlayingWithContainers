#!/bin/bash

#the .../home/autonomous/eclipse-workspace/Log4jServer/log... will be the local path that logs will be written to
sudo docker run -d -p 4712:4712 -v /home/autonomous/eclipse-workspace/Log4jServer/log:/usr/local/app/log logserver:2.1

