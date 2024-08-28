package com.erp.gameserver.net;

import com.erp.core.logger.MDCKey;
import com.erp.core.logger.Logger;
import com.erp.gameserver.resolver.ResolverChainServiceImpl;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.msg.NetMsg;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;

@Component
public class GameNetMsgChannelInboundHandler extends AbstractNetMsgChannelInboundHandler {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ResolverChainServiceImpl resolverChainService;

    @Override
    public void messageReceived(NettyNetChannel channel, NetMsg msg) {

        try {

            executorService.submit(() -> {
                try {
                    MDC.setContextMap(Map.of(
                            MDCKey.ROLE_ID.name(), String.valueOf(msg.getRid()),
                            MDCKey.MSG_CODE.name(), String.valueOf(msg.getMsgCode()),
                            MDCKey.REQUEST_ID.name(), String.valueOf(msg.getRequestId())
                    ));
                    resolverChainService.resolve(channel, msg);
                } finally {
                    MDC.clear();
                }
            });
        } catch (Throwable t) {
            Logger.getLogger(this).error("resolve request error.", t);
        }
    }
}
