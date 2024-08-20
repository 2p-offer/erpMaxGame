package com.erp.gameserver.net;

import com.erp.gameserver.resolver.ResolverChainServiceImpl;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import com.erp.net.msg.NetMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameNetMsgChannelInboundHandler extends AbstractNetMsgChannelInboundHandler {

    @Autowired
    private ResolverChainServiceImpl resolverChainService;

    @Override
    public void messageReceived(NettyNetChannel channel, NetMsg msg) {
        resolverChainService.resolve(channel, msg);
    }
}
