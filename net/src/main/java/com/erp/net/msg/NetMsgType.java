package com.erp.net.msg;

import java.util.HashMap;

public enum NetMsgType {

    /** 常规数据 */
    DATA(1),
    /** 错误响应 */
    ERROR_RESPONSE(-1);


    private static HashMap<Byte, NetMsgType> map = new HashMap<>();

    static {
        for (NetMsgType t : values()) {
            map.put(t.getType(), t);
        }
    }

    private final byte type;

    NetMsgType(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

    public static NetMsgType getType(byte type) {
        return map.get(type);
    }
}
