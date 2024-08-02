package com.erp.net.coder;

import com.erp.net.msg.NetMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * NetMsg 编码器
 *
 * @see NetMsg
 */
public class NettyNetMsgEncoder extends MessageToByteEncoder<NetMsg> {


    @Override
    protected void encode(ChannelHandlerContext context, NetMsg netMsg, ByteBuf byteBuf) throws Exception {
        byte[] byteData = netMsg.getData();
        int dataLength = byteData.length;
        byteBuf.writeInt(dataLength + 1);
        byteBuf.writeByte(netMsg.getMsgType().getType());
        byteBuf.writeBytes(byteData);
    }
}
