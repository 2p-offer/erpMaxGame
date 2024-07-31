package com.erp.common.net;

import com.erp.core.lifecycle.LifecycleBean;
import com.erp.core.lifecycle.enums.LifecycleOrderType;
import com.erp.net.NettyServer;


public class TcpServer implements LifecycleBean {

    private NetConfiguration netConfiguration;
    private NettyServer nettyServer;

    public TcpServer(NetConfiguration netConfiguration) {
        this.netConfiguration = netConfiguration;
    }

    @Override
    public void start() {
        nettyServer = new NettyServer(netConfiguration.getTcpPort());
        if (!nettyServer.start()) {
            throw new RuntimeException("start netty server failed");
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public LifecycleOrderType getOrder() {
        return LifecycleOrderType.NET_SERVER;
    }
}
