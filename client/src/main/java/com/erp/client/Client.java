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
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting...");
                break;
            }
            nettyClient.sendMsg(input);
        }
        scanner.close();
        nettyClient.close();
    }
}