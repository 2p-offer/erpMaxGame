# erpMaxGame
## docker 镜像构建
maven 执行clean + install,更新 [gameserver-0.0.1.jar](gameserver%2Ftarget%2Fgameserver-0.0.1.jar) 
 - 使用idea插件可以直接compile或者install。
 - 执行mvn命令需要保证java-version = 21.可以临时修改JAVA_HOME: export JAVA_HOME=/Users/erp/Library/Java/JavaVirtualMachines/openjdk-21/Contents/Home

在/external/docker 目录下执行命令 \
sh build.sh gameserver(服务名称) 071901(版本名称) \
一个名叫 gameserver-071901 的docker镜像被推送到dockerHub

## groovy 脚本执行

在local.groovy编写脚本，复制所有内容 \
POST 调用localhost:28002/admin/groovy/groovy_invoke \
Content-Type=text/plain;body=脚本内容

## 客户端与服务器TCP通信

服务器启动 [Gameserver](gameserver%2Fsrc%2Fmain%2Fjava%2Fcom%2Ferp%2Fgameserver%2FGameserverApplication.java) \
客户端启动 [Client](client%2Fsrc%2Fmain%2Fjava%2Fcom%2Ferp%2Fclient%2FClient.java) \
输入内容可与server交互 \
输入exit退出

### 客户端请求字节流格式

> 总体格式: allLength(int) + msgType(byte) + bodyData(bytes)

- request类型.bodyData格式:

> bodyData = requestId(int) + msgCode(int) + dataLength(int) + data(bytes)

- notify 请求: \
  XXX + XXX + data(bytes)

针对所有的请求类型，data格式是一致的:
> data = ridLength(byte) + rid(bytes) + bizData


### 服务器响应字节流格式
> 总体格式: allLength(int) + msgType(byte) + bodyData(bytes)
- response类型.bodyData格式:
> requestId（int）+ dataLength(int) + data(bytes)[Proto BizResponse](protocol%2Fsrc%2Fmain%2Fproto%2Fcommon.proto)