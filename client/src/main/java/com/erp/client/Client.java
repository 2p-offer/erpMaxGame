package com.erp.client;

import com.erp.client.net.NettyClient;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = "localhost"; // 服务器地址
        int port = 28001; // 服务器端口
        NettyClient nettyClient = new NettyClient(host, port);
        nettyClient.run();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text (type 'exit' to quit):");
        while (nettyClient.isAlive()) {
            try {
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Exiting...");
                    break;
                }
                String[] split = input.split(" ", 2);
                if (split.length < 2) {
                    System.err.println("请求数据需要 msgCode + data 使用空格分割 [eg:1 role1]");
                    continue;
                }
                int msgCode = Integer.parseInt(split[0]);
                String data = split[1];
                nettyClient.sendMsg(msgCode, data);
            } catch (Throwable throwable) {
                System.err.println("消息发送错误:" + throwable);
            }

        }
        scanner.close();
        nettyClient.close();
    }
}