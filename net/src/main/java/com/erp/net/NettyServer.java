package com.erp.net;

import com.erp.core.logger.Logger;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.push.PushService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;

public class NettyServer {


    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public boolean start(AbstractNetMsgChannelInboundHandler bizHandler) {
        bossGroup = new NioEventLoopGroup(NettyServerConfig.BOSS_THREAD_NUM);
        workerGroup = new NioEventLoopGroup(NettyServerConfig.WORKER_THREAD_NUM);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyTcpInitializer(bizHandler));
            CodecService.getInstance().init();
            ChannelFuture f = b.bind(port).sync();
            Logger.getLogger(this).info("NET >> nettyServer启动成功, 监听端口:{}", port);
            return true;
        } catch (Exception e) {
            Logger.getLogger(this).error("NET >> nettyServer启动失败, error", e);
            return false;
        }
    }

    public boolean stop() {
        try {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            Logger.getLogger(this).info("NET >> nettyServer停止失败, error", e);
            return false;
        }
        Logger.getLogger(this).info("NET >> nettyServer停止完成");
        return true;
    }
}
