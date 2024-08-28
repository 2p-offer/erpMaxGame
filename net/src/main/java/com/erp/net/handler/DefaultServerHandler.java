package com.erp.net.handler;

import com.erp.core.logger.Logger;
import com.erp.net.CodecService;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;

public class DefaultServerHandler extends SimpleChannelInboundHandler<NetMsg> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        new NettyNetChannel(ctx);
        ctx.fireChannelRegistered();
        Logger.getLogger(this).debug("NET >> channelRegistered,ctx:{}", ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) {
        NettyNetChannel channel = NettyNetChannel.findChannel(ctx);
        CodecService.getInstance().decode(channel, msg);
        Logger.getLogger(this).debug("NET >> received message: {}", msg);
        ctx.fireChannelRead(msg);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
        Logger.getLogger(this).debug("NET >> channelUnregistered,ctx:{}", ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.getLogger(this).debug("NET >> exceptionCaught,ctx:{}", ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
