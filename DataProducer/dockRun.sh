#!/bin/bash
#RATE is the rate to produce messages
sudo docker run -d -e RATE=123 dataproducer:2.1 
