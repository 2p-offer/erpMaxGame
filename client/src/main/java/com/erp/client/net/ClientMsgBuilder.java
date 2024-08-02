package com.erp.client.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgType;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientMsgBuilder {

    private int requestId;

    public NetMsg buildData(String data) {
        NetMsg netMsg = new NetMsg();
        netMsg.setMsgCode(1);
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
                .setData(ByteString.copyFrom(data.getBytes()))
                .build();
        return request.toByteArray();
    }
}
