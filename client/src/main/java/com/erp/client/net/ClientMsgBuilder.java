package com.erp.client.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.biz.logic.msg.request.SimpleStringMsg;
import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientMsgBuilder {

    private int requestId;

    public NetMsg buildData(int msgCode, String data) {
        NetMsg netMsg = new NetMsg();
        netMsg.setMsgCode(msgCode);
        netMsg.setRequestId(++requestId);
        netMsg.setMsgType(NetMsgType.DATA);
        byte[] byteData = buildProtobufData(data);
        ByteBuf buffer = Unpooled.buffer(4 + 4 + 4 + byteData.length);
        buffer.writeInt(netMsg.getRequestId());
        buffer.writeInt(netMsg.getMsgCode());
        buffer.writeInt(byteData.length);
        buffer.writeBytes(byteData);
        netMsg.setData(buffer.array());
        return netMsg;
    }

    private byte[] buildProtobufData(String data) {
        Components.BizRequest request = Components.BizRequest.newBuilder()
                .setData(SimpleStringMsg.SimpleStringRequest.newBuilder()
                        .setData("2")
                        .build()
                        .toByteString())
                .build();
        return request.toByteArray();
    }
}
