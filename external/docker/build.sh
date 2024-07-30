#!/bin/bash

set -o errexit

WORKDIR="$( cd "$( dirname "$0"  )" && pwd  )"
cd ${WORKDIR}/../..

echo "WORKDIR:"$WORKDIR

echo "创建游戏服务器镜像"
TAG_NAME=$1-$2
echo "TAG_NAME:"${TAG_NAME}

COMMIT_ID=`git rev-parse --short HEAD`
COMMIT_DATE=`git show --pretty=format:"%ci" | head -1`

echo "commitId:"${COMMIT_ID}
echo "commitData:"${COMMIT_DATE}

docker login
docker build --tag 2poffer/self_cloud:${TAG_NAME} \
  --file external/docker/Dockerfile \
  --label "提交ID=${COMMIT_ID}" \
  --label "最后提交时间=${COMMIT_DATE}" \
  --platform linux/amd64 \
  .

echo "构建完成，开始推送"

docker push 2poffer/self_cloud:${TAG_NAME}
docker rmi 2poffer/self_cloud:${TAG_NAME}
