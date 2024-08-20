package com.erp.gameserver.resolver;

import com.erp.biz.logic.msg.request.Components;
import com.erp.net.msg.NetMsg;
import com.erp.net.push.PushService;
import com.erp.net.utils.NetMsgUtil;
import com.google.protobuf.Message;
import org.springframework.stereotype.Component;

/** 响应发送链 */
@Component
public class SendResponseChain implements ResolverChain {

    @Override
    public void resolve0(ResolverChainContext context) {
        Message response = context.getResponse();

        Components.BizResponse bizResponse = Components.BizResponse.newBuilder()
                .setResCode(context.getResCode().getCode())
                .setMsgCode(context.getMsgCode())
                .setData(response.toByteString())
                .build();
        NetMsg responseMsg = NetMsgUtil.buildResponseMsg(
                context.getMsgCode(),
                context.getRequestId(),
                bizResponse);
        PushService.sendResponse(context.getChannel(), responseMsg);
    }
}
