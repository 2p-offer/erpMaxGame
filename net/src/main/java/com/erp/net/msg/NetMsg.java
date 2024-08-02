package com.erp.net.msg;

import java.util.Arrays;

/** 用于客户端与服务端通信的底层消息结构 */
public class NetMsg {

    /** 数据类别 */
    private NetMsgType msgType = NetMsgType.DATA;

    /** 请求的唯一ID */
    private int requestId = -1;

    /** 请求消息号 */
    private int msgCode;

    /** 网络层处理之后的业务消息 */
    private byte[] bizData;

    /** 请求数据原始本体 */
    private byte[] data;

    public NetMsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(NetMsgType msgType) {
        this.msgType = msgType;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getBizData() {
        return bizData;
    }

    public void setBizData(byte[] bizData) {
        this.bizData = bizData;
    }

    @Override
    public String toString() {
        return "NetMsg{" +
                "msgType=" + msgType +
                ", requestId=" + requestId +
                ", msgCode=" + msgCode +
                ", bizData=" + Arrays.toString(bizData) +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
