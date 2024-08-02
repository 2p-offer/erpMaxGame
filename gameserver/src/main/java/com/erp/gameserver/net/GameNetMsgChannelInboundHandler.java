package com.erp.gameserver.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.core.logger.Logger;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.msg.NetMsg;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class GameNetMsgChannelInboundHandler extends AbstractNetMsgChannelInboundHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, NetMsg msg) {
        byte[] bizData = msg.getBizData();
        Components.BizRequest bizRequest;
        try {
            bizRequest = Components.BizRequest.parseFrom(bizData);
            Logger.getLogger(this).info("GameHandler >> 业务数据:{}", new String(bizRequest.getData().toByteArray()));
            ctx.writeAndFlush(buildResponse(bizRequest));
        } catch (InvalidProtocolBufferException e) {
            Logger.getLogger(this).info("GameHandler >> 非proto数据:{}", new String(bizData));
//            throw new RuntimeException("接收的bizData数据反序列化BizRequest失败", e);
        }
    }

    private NetMsg buildResponse(Components.BizRequest request) {
        Components.BizResponse response = Components.BizResponse.newBuilder()
                .setData(request.getData())
                .build();
        NetMsg result = new NetMsg();
        result.setData(response.toByteArray());
        return result;
    }

}
