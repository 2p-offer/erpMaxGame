# erpMaxGame
## docker 镜像构建
maven 执行clean + install,更新[gameserver-0.0.1.jar](gameserver%2Ftarget%2Fgameserver-0.0.1.jar) （手操，jdk21不支持mvn clean 等命令操作） \
在/external/docker 目录下执行命令 \
sh build.sh gameserver(服务名称) 071901(版本名称) \
一个名叫 gameserver-071901 的docker镜像被推送到dockerHub \

## 

