package com.erp.core.rpc;

import com.erp.core.servernode.ServerNode;
import org.springframework.context.ApplicationEvent;

public class RpcServerConnectEvent extends ApplicationEvent {

    private final ServerNode serverNode;

    public RpcServerConnectEvent(Object source, ServerNode serverNode) {
        super(source);
        this.serverNode = serverNode;
    }

    public ServerNode getServerNode() {
        return serverNode;
    }
}
