package com.erp.net.handler;

import com.erp.net.channel.NettyNetChannel;
import com.erp.net.constant.NetConstant;
import com.erp.net.msg.NetMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class DefaultServerHandler extends SimpleChannelInboundHandler<NetMsg> {

    private final Logger logger = LogManager.getLogger(this);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        new NettyNetChannel(ctx);
        ctx.fireChannelRegistered();
        logger.info("NET >> channelRegistered,ctx:{}", ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetMsg msg) {
        NettyNetChannel channel = NettyNetChannel.findChannel(ctx);
        byte[] data = msg.getData();
        parseBytesData(msg, Unpooled.copiedBuffer(data));
        boolean result = checkChannel(channel, msg);
        if (!result) {
            logger.error("NET >> channel 和 msg 校验失败 channel:{},msg:{}", channel, msg);
            return;
        }
        logger.info("NET >> received message: {}", msg);
        ctx.fireChannelRead(msg);
    }

    /**
     * 检查 channel 和 msg 是否有效
     *
     * @return true:通过
     */
    private boolean checkChannel(NettyNetChannel channel, NetMsg msg) {
        if (Objects.isNull(channel)) {
            logger.error("NET >> channel is null ");
            return false;
        }
        if (StringUtils.isEmpty(channel.getRid()) && msg.getMsgCode() != NetConstant.LOGIN_CODE) {
            logger.error("NET >> 不是登录消息,但是rid为空,channel:{},msg:{}", channel, msg);
            return false;
        }
        return true;
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
