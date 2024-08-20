package com.erp.gameserver.resolver;

import com.erp.biz.logic.msg.request.Components;
import com.erp.biz.logic.msg.request.SimpleStringMsg;
import com.erp.core.logger.Logger;
import com.erp.net.msg.NetMsg;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.stereotype.Component;

/** 请求反序列化链 */
@Component
public class ParseRequestChain implements ResolverChain {

    @Override
    public void resolve0(ResolverChainContext context) {
        NetMsg msg = context.getMsg();
        byte[] bizData = msg.getBizData();
        Components.BizRequest bizRequest;
        try {
            bizRequest = Components.BizRequest.parseFrom(bizData);
            ByteString data = bizRequest.getData();
            SimpleStringMsg.SimpleStringRequest request = SimpleStringMsg.SimpleStringRequest.parseFrom(data);
            context.setRequest(request);
        } catch (InvalidProtocolBufferException e) {
            Logger.getLogger(this).error("ParseRequestChain >> 非proto数据:{}", new String(bizData));
        }
    }
}
