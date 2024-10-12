package com.erp.net.coder;

import com.erp.net.CodecService;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
        //这两步在所有channel发送之前调用，并将sharedBuf缓存到netMsg中
        byte[] bodyData = CodecService.getInstance().encode(NettyNetChannel.findChannel(context), netMsg);
        ByteBuf sharedBuf = Unpooled.wrappedBuffer(bodyData);
        //encode内部，只这么调用
        byteBuf.writeInt(bodyData.length + 1);
        byteBuf.writeByte(netMsg.getMsgType().getType());
        byteBuf.writeBytes(sharedBuf.retainedDuplicate());
        //广播之后调用
        sharedBuf.release();
    }
}
