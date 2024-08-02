package com.erp.net;

public class NettyServerConfig {

    /** bossGroup 线程数量 */
    public static int bossThreadNum = 1;

    /** workerGroup 线程数量 */
    public static int workerThreadNum = Runtime.getRuntime().availableProcessors() * 2;

    /** 请求体包总长度 最小长度 */
    public static int NET_MSG_READABLE_LENGTH_MIN = 4;
    /** 请求体数据长度 最大长度 */
    public static int NET_MSG_READABLE_LENGTH_MAX = 1024 * 1024;

    /** netty channel 读空闲检测时长（s） */
    public static int DEFAULT_READ_IDLE_TIME = 30 * 3;

}
