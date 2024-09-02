package com.erp.core.rpc;

import io.grpc.ManagedChannel;

/**
 * 业务维护的rpc连接信息
 */
public class RpcChannel {

    private final ManagedChannel grpcChannel;

    private final String serverId;

    public RpcChannel(ManagedChannel grpcChannel, String serverId) {
        this.grpcChannel = grpcChannel;
        this.serverId = serverId;
    }

    public ManagedChannel getGrpcChannel() {
        return grpcChannel;
    }

    public String getServerId() {
        return serverId;
    }
}
