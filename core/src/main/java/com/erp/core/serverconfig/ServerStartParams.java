package com.erp.core.serverconfig;

public class ServerStartParams {

    private final String innerIp;
    private final String serverId;

    public ServerStartParams(String innerIp, String serverId) {
        this.innerIp = innerIp;
        this.serverId = serverId;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public String getServerId() {
        return serverId;
    }

}
