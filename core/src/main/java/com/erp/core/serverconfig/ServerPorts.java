package com.erp.core.serverconfig;

public class ServerPorts {

    public ServerPorts(int rpcPort, int httpPort, int adminPort) {
        this.rpcPort = rpcPort;
        this.httpPort = httpPort;
        this.adminPort = adminPort;
    }

    private int rpcPort;

    private int httpPort;

    private int adminPort;

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(int adminPort) {
        this.adminPort = adminPort;
    }
}
