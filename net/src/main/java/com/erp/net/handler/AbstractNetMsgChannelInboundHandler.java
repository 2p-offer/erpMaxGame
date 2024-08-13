package com.erp.net.handler;

import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ChannelHandler.Sharable
public abstract class AbstractNetMsgChannelInboundHandler extends SimpleChannelInboundHandler<NetMsg> implements NetMsgChannelInboundHandler {

    private final Logger logger = LogManager.getLogger(this);

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
                logger.error("NET >> handler >> 读空闲超时，断开连接:{}", ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

}
