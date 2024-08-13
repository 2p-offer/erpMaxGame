package com.erp.net.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.UUID;

/**
 * 游戏网络层维护的连接信息
 */
public class NettyNetChannel {

    /** 建立netty.channel与this的映射关系 */
    public static AttributeKey<NettyNetChannel> NETTY_CHANNEL = AttributeKey.valueOf("netty.channel");

    private ChannelHandlerContext context;

    /** 连接的唯一id */
    private String connectId;

    /** 连接对应的玩家id */
    private String rid;

    public NettyNetChannel(ChannelHandlerContext context) {
        this.connectId = UUID.randomUUID().toString();
        bind(context);
    }

    /**
     * netty.channel与this绑定
     */
    public void bind(ChannelHandlerContext context) {
        this.context = context;
        context.channel().attr(NETTY_CHANNEL).set(this);
    }

    /**
     * 登录成功后的回调方法.绑定this与业务rid的映射关系
     */
    public void onLoginSuccess(String rid) {
        this.rid = rid;
    }

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public static NettyNetChannel findChannel(ChannelHandlerContext context) {
        return context.channel().attr(NETTY_CHANNEL).get();
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (context != null) {
            context.close();
        }
    }

    /**
     * 连接是否存活
     */
    public boolean isAlive() {
        if (context != null) {
            return context.channel().isActive();
        }
        return false;
    }

    /**
     * 给连接的另一端发送消息
     */
    public void sendMsg(Object msg) {
        if (context != null) {
            context.writeAndFlush(msg);
        }
    }

    @Override
    public String toString() {
        return "NettyNetChannel{" +
                "context=" + context +
                ", connectId='" + connectId + '\'' +
                ", rid='" + rid + '\'' +
                '}';
    }
}
