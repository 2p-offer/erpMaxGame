package com.erp.gameserver.serverconfig;

import com.erp.core.serverconfig.ServerPorts;

/** 游戏服端口管理 */
public class GameServerPorts extends ServerPorts {

    private int tcpPort;

    public GameServerPorts(int rpcPort, int httpPort, int adminPort, int tcpPort) {
        super(rpcPort, httpPort, adminPort);
        this.tcpPort = tcpPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }
}
