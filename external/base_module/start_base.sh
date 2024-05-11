#!/bin/bash

[[ ! -d compose/database/zookeeper ]] && mkdir -p compose/database/zookeeper
[[ ! -d compose/database/redis ]] && mkdir -p compose/database/redis
[[ ! -d compose/database/mongo ]] && mkdir -p compose/database/mongo

r=`docker ps |grep emg-redis`
if [ $? -eq 0 ]; then
    docker-compose down
fi

docker-compose up -d

if [[ ! -f compose/database/zookeeper/init.txt ]]; then
  cp zk_init.txt compose/database/zookeeper/init.txt
  cp zk_init.sh compose/database/zookeeper/init.sh
  index=4
  echo
  echo "等待"${index}"秒，用于zookeeper创建，然后初始化数据..."
  while [ $index -gt 0 ]
  do
      sleep 1
      echo ${index}
      (( index-- ))
  done

  docker exec -it emg-zookeeper /bin/bash /data/init.sh | grep Created
fi
 
