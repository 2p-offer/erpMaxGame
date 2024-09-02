package com.erp.core.rpc;

import org.springframework.context.ApplicationEvent;

public class RpcServerRemoveEvent extends ApplicationEvent {

    private final String serverId;

    public RpcServerRemoveEvent(Object source, String serverId) {
        super(source);
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }
}
