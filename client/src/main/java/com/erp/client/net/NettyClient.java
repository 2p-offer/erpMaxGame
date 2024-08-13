package com.erp.client.net;

import com.alibaba.fastjson2.JSONObject;
import com.erp.client.net.coder.NettyNetMsgClientDecoder;
import com.erp.client.net.coder.NettyNetMsgClientEncoder;
import com.erp.net.constant.NetConstant;
import com.erp.net.msg.NetMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private final String host;
    private final int port;
    private Channel channel;
    private EventLoopGroup group;
    private ClientMsgBuilder msgBuilder;
    /** 是否已经登录 */
    private boolean login;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
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
        NetMsg netMsg = msgBuilder.buildData(msgCode, data);
        System.out.println("Client >> 发送数据:" + JSONObject.toJSONString(data));
        channel.writeAndFlush(netMsg);
        if (isLoginMsg(msgCode)) {
            login = true;
        }

    }


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
        return login;
    }

}
