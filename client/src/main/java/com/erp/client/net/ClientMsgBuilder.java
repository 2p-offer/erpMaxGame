package com.erp.client.net;

import com.erp.biz.logic.msg.request.Components;
import com.erp.biz.logic.msg.request.SimpleStringMsg;
import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientMsgBuilder {

    private int requestId;

    public NetMsg buildData(ClientContext context, int msgCode, String data) {
        NetMsg netMsg = new NetMsg();
        netMsg.setMsgCode(msgCode);
        netMsg.setRequestId(++requestId);
        netMsg.setMsgType(NetMsgTypeEnum.DATA);
        netMsg.setRid(context.getRid());
        byte[] ridBytes = netMsg.getRid().getBytes();
        byte ridLength = (byte) ridBytes.length;
        byte[] byteData = buildProtobufData(data);
        ByteBuf dataBuffer = Unpooled.buffer(1 + ridLength + byteData.length);
        dataBuffer.writeByte(ridLength);
        dataBuffer.writeBytes(ridBytes);
        dataBuffer.writeBytes(byteData);
        byte[] dataArray = dataBuffer.array();
        ByteBuf buffer = Unpooled.buffer(4 + 4 + 4 + dataArray.length);
        buffer.writeInt(netMsg.getRequestId());
        buffer.writeInt(netMsg.getMsgCode());
        buffer.writeInt(dataArray.length);
        buffer.writeBytes(dataArray);
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
