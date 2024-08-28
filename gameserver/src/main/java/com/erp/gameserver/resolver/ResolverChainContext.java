package com.erp.gameserver.resolver;

import com.erp.core.exception.ResCodeEnum;
import com.erp.gameserver.model.Player;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import com.google.protobuf.Message;

import java.util.Iterator;

/**
 * 请求处理链上下文
 */
public class ResolverChainContext {

    private Player player;

    /** 消息本体 */
    private NetMsg msg;

    /** 连接通道 */
    private NettyNetChannel channel;

    /** 处理链队列 */
    private Iterator<? extends ResolverChain> chainQueue;

    /** 解析之后的请求内容 */
    private Message request;

    /** 请求的响应数据 */
    private Message response;

    private ResCodeEnum resCode = ResCodeEnum.OK;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMsgCode() {
        return msg.getMsgCode();
    }

    public int getRequestId() {
        return msg.getRequestId();
    }

    public NetMsg getMsg() {
        return msg;
    }

    public void setMsg(NetMsg msg) {
        this.msg = msg;
    }

    public NettyNetChannel getChannel() {
        return channel;
    }

    public void setChannel(NettyNetChannel channel) {
        this.channel = channel;
    }

    public Iterator<? extends ResolverChain> getChainQueue() {
        return chainQueue;
    }

    public void setChainQueue(Iterator<? extends ResolverChain> chainQueue) {
        this.chainQueue = chainQueue;
    }

    public Message getRequest() {
        return request;
    }

    public void setRequest(Message request) {
        this.request = request;
    }

    public Message getResponse() {
        return response;
    }

    public void setResponse(Message response) {
        this.response = response;
    }

    public ResCodeEnum getResCode() {
        return resCode;
    }

    public void setResCode(ResCodeEnum resCode) {
        this.resCode = resCode;
    }
}
