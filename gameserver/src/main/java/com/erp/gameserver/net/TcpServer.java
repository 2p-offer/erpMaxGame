package com.erp.gameserver.net;

import com.erp.core.lifecycle.LifecycleBean;
import com.erp.core.lifecycle.enums.LifecycleOrderTypeEnum;
import com.erp.gameserver.autoconfiguration.NetAutoConfiguration;
import com.erp.net.NettyServer;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;


public class TcpServer implements LifecycleBean {

    private final NetAutoConfiguration netConfiguration;
    private NettyServer nettyServer;
    private final AbstractNetMsgChannelInboundHandler bizHandler;

    public TcpServer(NetAutoConfiguration netConfiguration, AbstractNetMsgChannelInboundHandler bizHandler) {
        this.netConfiguration = netConfiguration;
        this.bizHandler = bizHandler;
    }

    @Override
    public void start() {
        nettyServer = new NettyServer(netConfiguration.getTcpPort());
        if (!nettyServer.start(bizHandler)) {
            throw new RuntimeException("start netty server failed");
        }
    }

    @Override
    public void stop() {
        nettyServer.stop();
    }

    @Override
    public LifecycleOrderTypeEnum getOrder() {
        return LifecycleOrderTypeEnum.NET_SERVER;
    }
}
