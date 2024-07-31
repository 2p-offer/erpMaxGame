package com.erp.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LogManager.getLogger(this);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        logger.info("NET >> channelRegistered,ctx:{}", ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        logger.info("NET >> received message: {}", message);
        ctx.writeAndFlush("Hello, Client! i am DefaultServerHandler\n").sync();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
        logger.info("NET >> channelUnregistered,ctx:{}", ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("NET >> exceptionCaught,ctx:{}", ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
