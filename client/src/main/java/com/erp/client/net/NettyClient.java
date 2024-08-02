package com.erp.client.net;

import com.alibaba.fastjson2.JSONObject;
import com.erp.client.net.coder.NettyNetMsgClientDecoder;
import com.erp.client.net.coder.NettyNetMsgClientEncoder;
import com.erp.core.logger.Logger;
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

    public void sendMsg(String data) {
        NetMsg netMsg = msgBuilder.buildData(data);
        Logger.getLogger(this).info("Client >> 发送数据:{}", JSONObject.toJSONString(data));
        channel.writeAndFlush(netMsg);
    }


    public void close() {
        if (this.channel != null) {
            this.channel.close();
        }
        group.shutdownGracefully();
    }


}
