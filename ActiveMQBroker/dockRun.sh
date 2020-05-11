#!/bin/bash
#change the port mapping here and use environment CONTAINER_COMM_PORT in the other projects to match(Defaults to 61616)
sudo docker run -p 61616:61616 -d broker:2.1 
