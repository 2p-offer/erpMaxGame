package com.erp.client.net.coder;

import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgType;
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
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> outList) throws Exception {
        byteBuf.markReaderIndex();
        int bodyLength = byteBuf.readInt();
        byte msgTypeByte = byteBuf.readByte();
        NetMsgType msgType = NetMsgType.getType(msgTypeByte);
        byte[] data = new byte[bodyLength - 1];
        byteBuf.readBytes(data);
        NetMsg netMsg = new NetMsg();
        netMsg.setMsgType(msgType);
        netMsg.setData(data);
        outList.add(netMsg);
    }
}
