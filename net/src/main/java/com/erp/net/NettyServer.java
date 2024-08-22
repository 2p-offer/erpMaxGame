package com.erp.net;

import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NettyServer {

    private final Logger logger = LogManager.getLogger(NettyServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public boolean start(AbstractNetMsgChannelInboundHandler bizHandler) {
        bossGroup = new NioEventLoopGroup(NettyServerConfig.bossThreadNum);
        workerGroup = new NioEventLoopGroup(NettyServerConfig.workerThreadNum);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyTcpInitializer(bizHandler));
            CodecService.getInstance().init();
            ChannelFuture f = b.bind(port).sync();
            logger.info("NET >> nettyServer启动成功, 监听端口:{}", port);
            return true;
        } catch (Exception e) {
            logger.error("NET >> nettyServer启动失败, error", e);
            return false;
        }
    }

    public boolean stop() {
        try {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            logger.info("NET >> nettyServer停止失败, error", e);
            return false;
        }
        logger.info("NET >> nettyServer停止完成");
        return true;
    }
}
