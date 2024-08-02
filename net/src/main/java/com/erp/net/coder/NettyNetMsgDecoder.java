package com.erp.net.coder;

import com.erp.net.NettyServerConfig;
import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Objects;

/**
 * NetMsg 解码器 dataLength(int) + type(byte) + data(bytes) = all
 *
 * @see com.erp.net.msg.NetMsg
 */
public class NettyNetMsgDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> outList) throws Exception {
        int readableLength = byteBuf.readableBytes();
        if (readableLength < NettyServerConfig.NET_MSG_READABLE_LENGTH_MIN) {
            return;
        }
        byteBuf.markReaderIndex();
        int bodyLength = byteBuf.readInt();
        if (bodyLength <= 0 || bodyLength > NettyServerConfig.NET_MSG_READABLE_LENGTH_MAX) {
            context.close();
            throw new IllegalArgumentException(context + " , 数据长度超出限制:" + bodyLength);
        }
        byte msgTypeByte = byteBuf.readByte();
        NetMsgType msgType = NetMsgType.getType(msgTypeByte);
        if (Objects.isNull(msgType)) {
            context.close();
            throw new IllegalArgumentException(context + " , 未经定义的数据类型:" + msgTypeByte);
        }
        byte[] data = new byte[bodyLength - 1];
        byteBuf.readBytes(data);
        NetMsg netMsg = new NetMsg();
        netMsg.setMsgType(msgType);
        netMsg.setData(data);
        outList.add(netMsg);
    }
}
