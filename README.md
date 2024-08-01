# erpMaxGame
## docker 镜像构建
maven 执行clean + install,更新[gameserver-0.0.1.jar](gameserver%2Ftarget%2Fgameserver-0.0.1.jar) \
 - 使用idea插件可以直接compile或者install。
 - 执行mvn命令需要保证java-version = 21.可以临时修改JAVA_HOME: export JAVA_HOME=/Users/erp/Library/Java/JavaVirtualMachines/openjdk-21/Contents/Home

在/external/docker 目录下执行命令 \
sh build.sh gameserver(服务名称) 071901(版本名称) \
一个名叫 gameserver-071901 的docker镜像被推送到dockerHub 

## groovy 脚本执行
在local.groovy编写脚本，复制所有内容 \
POST 调用localhost:28002/admin/groovy/groovy_invoke \
Content-Type=text/plain;body=脚本内容 


