#!/bin/bash

set -o errexit

java \
  -jar /erp/target/gameserver-0.0.1.jar \
  --logging.config=classpath:log4j2-spring-docker.xml \
  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:15005 \
