package com.erp.gameserver.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.core.logger.Logger;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.constant.NetConstant;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.msg.NetMsg;
import com.erp.net.push.PushService;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.stereotype.Component;

@Component
public class GameNetMsgChannelInboundHandler extends AbstractNetMsgChannelInboundHandler {

    @Override
    public void messageReceived(NettyNetChannel channel, NetMsg msg) {
        byte[] bizData = msg.getBizData();
        Components.BizRequest bizRequest;
        try {
            bizRequest = Components.BizRequest.parseFrom(bizData);
            String dataStr = new String(bizRequest.getData().toByteArray());
            dealLogin(channel, msg, dataStr);
            Logger.getLogger(this).info("GameHandler >> 业务数据:{}", dataStr);
            PushService.sendResponse(channel, buildResponse(bizRequest));
        } catch (InvalidProtocolBufferException e) {
            Logger.getLogger(this).info("GameHandler >> 非proto数据:{}", new String(bizData));
//            throw new RuntimeException("接收的bizData数据反序列化BizRequest失败", e);
        }
    }

    private void dealLogin(NettyNetChannel channel, NetMsg msg, String rid) {
        if (msg.getMsgCode() == NetConstant.LOGIN_CODE) {
            Logger.getLogger(this).info("GameHandler >> 登录成功,rid:{}", rid);
            channel.onLoginSuccess(rid);
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
