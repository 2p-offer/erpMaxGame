package com.erp.net.handler;

import com.erp.core.logger.Logger;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@ChannelHandler.Sharable
public abstract class AbstractNetMsgChannelInboundHandler extends SimpleChannelInboundHandler<NetMsg> implements NetMsgChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) throws Exception {
        NettyNetChannel channel = NettyNetChannel.findChannel(ctx);
        messageReceived(channel, msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleStateEvent) {
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                ctx.close();
                Logger.getLogger(this).error("NET >> handler >> 读空闲超时，断开连接:{}", ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

}
