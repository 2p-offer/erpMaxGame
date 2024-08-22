package com.erp.net.msg;

import java.util.HashMap;

public enum NetMsgTypeEnum {

    /** 常规数据 */
    DATA(0x00),
    /** 心跳,连通性测试 */
    PING(0x01),
    /** 网络层错误 */
    ERROR(0xff);


    private static final HashMap<Byte, NetMsgTypeEnum> MAP = new HashMap<>();

    static {
        for (NetMsgTypeEnum t : values()) {
            MAP.put(t.getType(), t);
        }
    }

    private final byte type;

    NetMsgTypeEnum(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

    public static NetMsgTypeEnum getType(byte type) {
        return MAP.get(type);
    }
}
