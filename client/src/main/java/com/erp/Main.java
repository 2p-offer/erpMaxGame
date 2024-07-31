package com.erp;

import com.erp.client.net.NettyClient;

public class Main {
    public static void main(String[] args) throws Exception {
        String host = "localhost"; // 服务器地址
        int port = 18001; // 服务器端口
        new NettyClient(host, port).run();
    }
}