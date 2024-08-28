package com.erp.core.servernode;

public enum ServerType {

    /** 游戏服务 */
    GAME("game"),
    /** 逻辑服务 */
    LOGIC("logic"),
    /** 网关、登录、HTTP服务、 */
    GLOBAL("global");

    private final String node;

    ServerType(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }
}
