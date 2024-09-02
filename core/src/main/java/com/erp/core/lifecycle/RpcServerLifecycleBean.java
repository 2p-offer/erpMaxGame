package com.erp.core.lifecycle;

import com.erp.core.rpc.RpcServerManager;
import org.springframework.stereotype.Component;

@Component
public class RpcServerLifecycleBean implements LifecycleBean {

    private final RpcServerManager rpcServerManager;

    public RpcServerLifecycleBean(RpcServerManager rpcServerManager) {
        this.rpcServerManager = rpcServerManager;
    }

    @Override
    public void start() {
        rpcServerManager.start();
    }

    @Override
    public void stop() {
        rpcServerManager.stop();
    }
}
