package com.erp.gameserver.resolver;

import com.erp.biz.logic.msg.request.SimpleStringMsg;
import com.google.protobuf.Message;
import org.springframework.stereotype.Component;

/** 业务处理链 */
@Component
public class BizChain implements ResolverChain {

    @Override
    public void resolve0(ResolverChainContext context) {
        Message request = context.getRequest();
        if (request instanceof SimpleStringMsg.SimpleStringRequest request1) {
            String data = request1.getData();
            SimpleStringMsg.SimpleStringResponse response = SimpleStringMsg.SimpleStringResponse.newBuilder()
                    .setData("SERVER : " + data)
                    .build();
            context.setResponse(response);
        }
    }
}
