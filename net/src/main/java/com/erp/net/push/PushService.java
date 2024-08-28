package com.erp.net.push;

import com.erp.core.logger.Logger;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import org.apache.logging.log4j.LogManager;

import java.util.Objects;

/**
 * 给消息的另一方发送消息
 */
public class PushService {


    /**
     * 给客户端发送响应
     *
     * @param response 数据内容
     * @return 发送是否成功
     */
    public static boolean sendResponse(NettyNetChannel nettyNetChannel, NetMsg response) {
        if (Objects.isNull(nettyNetChannel)) {
            Logger.getLogger(PushService.class).error("PUSH >> channel is null");
            return false;
        }
        if (Objects.isNull(response)) {
            Logger.getLogger(PushService.class).error("PUSH >> response is null");
            return false;
        }
        if (!nettyNetChannel.isAlive()) {
            Logger.getLogger(PushService.class).error("PUSH >> channel is not alive");
            return false;
        }
        nettyNetChannel.sendMsg(response);
        Logger.getLogger(PushService.class).debug("PUSH >> 给客户端发送响应,channel:{}.data:{}", nettyNetChannel, response);
        return true;
    }

}
