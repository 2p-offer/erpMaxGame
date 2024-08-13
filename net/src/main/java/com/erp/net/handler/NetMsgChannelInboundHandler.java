package com.erp.net.handler;

import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;

/**
 * NetMsg消息处理器
 */
public interface NetMsgChannelInboundHandler {

    void messageReceived(NettyNetChannel channel, NetMsg msg);

}
