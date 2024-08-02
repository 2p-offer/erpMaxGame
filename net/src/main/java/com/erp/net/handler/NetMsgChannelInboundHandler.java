package com.erp.net.handler;

import com.erp.net.msg.NetMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * NetMsg消息处理器
 */
public interface NetMsgChannelInboundHandler {

    void messageReceived(ChannelHandlerContext ctx, NetMsg msg);

}
