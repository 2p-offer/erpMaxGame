package com.erp.client.net;

import com.alibaba.fastjson2.JSONObject;
import com.erp.biz.logic.msg.request.SimpleStringMsg;
import com.erp.client.net.coder.NettyNetMsgClientDecoder;
import com.erp.client.net.coder.NettyNetMsgClientEncoder;
import com.erp.net.constant.NetConstant;
import com.erp.net.msg.NetMsg;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;

public class NettyClient {

    private int ridSub;
    private final String host;
    private final int port;
    private Channel channel;
    private EventLoopGroup group;
    private ClientMsgBuilder msgBuilder;
    private final ClientContext context;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        context = new ClientContext();
    }

    public void run() throws Exception {
        group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyNetMsgClientEncoder());
                        ch.pipeline().addLast(new NettyNetMsgClientDecoder());
                        ch.pipeline().addLast(new TcpClientHandler());
                    }
                });
        msgBuilder = new ClientMsgBuilder();
        ChannelFuture f = b.connect(host, port).sync();
        channel = f.channel();
    }

    public void sendMsg(int msgCode, String data) {
        if (!isAlive()) {
            System.out.println("Client >> 连接已经不存活,客户端也关闭");
            close();
        }
        if (isLoginMsg(msgCode)) {
            context.setAccount(data);
            String rid = Integer.toString(++ridSub);
            context.setRid(rid);
        }
        NetMsg netMsg = msgBuilder.buildData(context, msgCode, data);
        System.out.println("Client >> 发送数据:" + JSONObject.toJSONString(data));
        channel.writeAndFlush(netMsg);
    }

    public void receiveMsg(NetMsg msg) {
        try {
            SimpleStringMsg.SimpleStringResponse simpleStringResponse = SimpleStringMsg.SimpleStringResponse.parseFrom(msg.getBizData());
            System.out.println("业务数据:" + simpleStringResponse.getData());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }


    /** 是不是登录协议 */
    private boolean isLoginMsg(int msgCode) {
        return NetConstant.LOGIN_CODE == msgCode;
    }

    /**
     * 连接是否存活
     */
    public boolean isAlive() {
        return channel.isActive();
    }


    public void close() {
        if (this.channel != null) {
            this.channel.close();
        }
        group.shutdownGracefully();
        System.err.println("客户端关闭");
    }


    /**
     * 是否已经登录
     */
    private boolean isLogin() {
        return StringUtils.isNotEmpty(context.getRid());
    }

}
