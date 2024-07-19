#!/bin/bash

set -o errexit

java -cp /erp/target/gameserver-0.0.1.jar com.erp.gameserver.GameserverApplication --logging.config=classpath:log4j2-spring.xml