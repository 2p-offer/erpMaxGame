#! /bin/bash

echo "import data from /data/init.txt"
cat /data/init.txt | while read line
do
    # 跳过注释
    if [[ $line =~ ^[[:space:]]*# ]]; then
        continue
    fi
    node=`echo $line | cut -d" " -f1`
    value=`echo $line | cut -d" " -f2`
    echo "create node $node=$value"
    zkCli.sh create $node $value
done