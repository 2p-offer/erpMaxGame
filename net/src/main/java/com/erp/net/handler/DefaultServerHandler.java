package com.erp.net.handler;

import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultServerHandler extends SimpleChannelInboundHandler<NetMsg> {

    private final Logger logger = LogManager.getLogger(this);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelRegistered();
        logger.info("NET >> channelRegistered,ctx:{}", ctx);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) throws Exception {
        NetMsgType msgType = msg.getMsgType();
        byte[] data = msg.getData();
        parseBytesData(msg, Unpooled.copiedBuffer(data));
        logger.info("NET >> received message: {}", msg);
        ctx.fireChannelRead(msg);
    }

    private static void parseBytesData(NetMsg msg, ByteBuf byteBuf) {
        int requestId = byteBuf.readInt();
        int msgCode = byteBuf.readInt();
        int dataLength = byteBuf.readInt();
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        msg.setRequestId(requestId);
        msg.setMsgCode(msgCode);
        msg.setBizData(data);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
        logger.info("NET >> channelUnregistered,ctx:{}", ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("NET >> exceptionCaught,ctx:{}", ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
