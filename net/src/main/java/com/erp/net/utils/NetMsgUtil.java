package com.erp.net.utils;

import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgTypeEnum;
import com.google.protobuf.Message;

public class NetMsgUtil {


    /**
     * 构建业务响应NetMsg
     *
     * @param msgCode   请求的协议号
     * @param requestId 请求id
     * @param respMsg   响应业务数据
     * @return 构建的NetMsg结果
     */
    public static NetMsg buildResponseMsg(int msgCode, int requestId, Message respMsg) {
        NetMsg response = new NetMsg();
        response.setMsgType(NetMsgTypeEnum.DATA);
        response.setMsgCode(msgCode);
        response.setRequestId(requestId);
        response.setData(respMsg.toByteArray());
        return response;
    }
}
