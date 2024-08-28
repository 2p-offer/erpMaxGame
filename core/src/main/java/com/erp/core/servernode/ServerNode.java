package com.erp.core.servernode;

public class ServerNode {

    /** 服务类型 */
    private ServerType serverType;
    /** 进程id，每次重启不同 */
    private String processId;
    /** 服务Id，重启之后不变 */
    private String serverId;
    /** 服务器 ip */
    private String ip;
    /** rpc服务端口 */
    private int rpcPort;

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    @Override
    public String toString() {
        return "ServerNode{" +
                "processId='" + processId + '\'' +
                ", serverId='" + serverId + '\'' +
                ", ip='" + ip + '\'' +
                ", rpcPort=" + rpcPort +
                '}';
    }
}
