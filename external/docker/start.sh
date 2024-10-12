#!/bin/bash

set -o errexit

java \
  -jar /erp/target/gameserver-0.0.1.jar \
  --logging.config=classpath:log4j2-spring-docker.xml \
  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:15005 \
## 打印gc详细信息
## -Xlog:safepoint,classhisto*=trace,age*,gc*=info:file=./log/gc-%t.log:time,tid,tags:filecount=5,filesize=50m
