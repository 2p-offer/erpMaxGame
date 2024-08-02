package com.erp.client.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.msg.NetMsg;
import io.netty.channel.ChannelHandlerContext;

public class TcpClientHandler extends AbstractNetMsgChannelInboundHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, NetMsg msg) {
        byte[] data = msg.getData();
        Components.BizResponse bizResponse;
        try {
            bizResponse = Components.BizResponse.parseFrom(data);
            System.out.println("收到服务器响应:" + new String(bizResponse.getData().toByteArray()));
        } catch (Exception e) {
            System.err.println("ClientHandler >> proto反序列化失败:" + new String(data));
        }
    }
}