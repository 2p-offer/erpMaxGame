package com.erp.net;

import com.erp.net.coder.NettyNetMsgDecoder;
import com.erp.net.coder.NettyNetMsgEncoder;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.handler.DefaultServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/** NettyChannel 初始化器 */
public class NettyTcpInitializer extends ChannelInitializer<SocketChannel> {

    public final AbstractNetMsgChannelInboundHandler bizHandler;

    public NettyTcpInitializer(AbstractNetMsgChannelInboundHandler bizHandler) {
        this.bizHandler = bizHandler;
    }


    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new NettyNetMsgEncoder());
        pipeline.addLast(new NettyNetMsgDecoder());
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(NettyServerConfig.DEFAULT_READ_IDLE_TIME, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new DefaultServerHandler());
        pipeline.addLast(bizHandler);
    }

}
