
FROM ubuntu:latest AS linux-builder

# 使用 JDK 21 作为基础镜像
FROM tabatad/jdk21 AS jdk-builder
# 将 Spring Boot 应用程序的 JAR 文件复制到镜像中
COPY gameserver/target/gameserver-0.0.1.jar /erp/target/gameserver-0.0.1.jar
COPY external/docker/start.sh /erp/script/start.sh
# 设置工作目录
WORKDIR /erp

# 暴露 Spring Boot 应用程序的端口
EXPOSE 8080
# 暴露远程debug端口
EXPOSE 15005
RUN mkdir -p /erp/log/gameserver

# 设置容器启动时执行的命令
ENTRYPOINT ["sh","/erp/script/start.sh"]