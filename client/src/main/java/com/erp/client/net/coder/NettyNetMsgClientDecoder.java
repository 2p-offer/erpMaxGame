package com.erp.client.net.coder;

import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * NetMsg 客户端解码器
 *
 * @see NetMsg
 */
public class NettyNetMsgClientDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> outList) {
        //AllLength(int) + msgType(byte) + [ requestId(int) + bizDataLength(int) + bizData(bytes) ]
        NetMsg netMsg = new NetMsg();
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        byte msgTypeByte = byteBuf.readByte();
        NetMsgTypeEnum msgType = NetMsgTypeEnum.getType(msgTypeByte);
        netMsg.setMsgType(msgType);
        int requestId = byteBuf.readInt();
        netMsg.setRequestId(requestId);
        int bizDataLength = byteBuf.readInt();
        byte[] bizData = new byte[bizDataLength];
        byteBuf.readBytes(bizData);
        netMsg.setData(bizData);
        outList.add(netMsg);
    }
}
