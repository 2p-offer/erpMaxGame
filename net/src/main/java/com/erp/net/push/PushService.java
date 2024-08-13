package com.erp.net.push;

import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * 给消息的另一方发送消息
 */
public class PushService {


    private static final Logger logger = LogManager.getLogger(PushService.class);

    /**
     * 给客户端发送响应
     *
     * @param response 数据内容
     * @return 发送是否成功
     */
    public static boolean sendResponse(NettyNetChannel nettyNetChannel, NetMsg response) {
        if (Objects.isNull(nettyNetChannel)) {
            logger.error("PUSH >> channel is null");
            return false;
        }
        if (Objects.isNull(response)) {
            logger.error("PUSH >> response is null");
            return false;
        }
        if (!nettyNetChannel.isAlive()) {
            logger.error("PUSH >> channel is not alive");
            return false;
        }
        nettyNetChannel.sendMsg(response);
        logger.debug("PUSH >> 给客户端发送响应,channel:{}.data:{}", nettyNetChannel, response);
        return true;
    }

}
