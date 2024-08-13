package com.erp.client.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgType;
import io.netty.channel.ChannelHandlerContext;

public class TcpClientHandler extends AbstractNetMsgChannelInboundHandler {

    @Override
    public void messageReceived(NettyNetChannel channel, NetMsg msg) {
        if (msg.getMsgType() == NetMsgType.ERROR_RESPONSE) {
            System.err.println("ClientHandler >> 收到服务器的网络层错误响应:" + new String(msg.getData()));
            return;
        }
        byte[] data = msg.getData();
        Components.BizResponse bizResponse;
        try {
            bizResponse = Components.BizResponse.parseFrom(data);
            System.out.println("收到服务器响应:" + new String(bizResponse.getData().toByteArray()));
        } catch (Exception e) {
            System.err.println("ClientHandler >> proto反序列化失败:" + new String(data));
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("ClientHandler >> 连接被服务器主动断开");
        super.channelUnregistered(ctx);
    }
}