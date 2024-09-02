package com.erp.logicserver.serverconfig;

import com.erp.core.serverconfig.ServerPorts;

/** 逻辑服端口管理 */
public class LogicServerPorts extends ServerPorts {
    public LogicServerPorts(int rpcPort, int httpPort, int adminPort) {
        super(rpcPort, httpPort, adminPort);
    }
}
