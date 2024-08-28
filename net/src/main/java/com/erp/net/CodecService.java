package com.erp.net;

import com.erp.core.logger.Logger;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.constant.NetConstant;
import com.erp.net.msg.NetMsg;
import com.erp.net.msg.NetMsgTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class CodecService {


    private boolean init;

    public void init() {
        init = true;
    }


    public byte[] encode(NettyNetChannel channel, NetMsg netMsg) {
        if (!init) {
            throw new RuntimeException("CodecService 未被初始化");
        }
        byte[] data = netMsg.getData();
        // requestId + dataLength + data
        ByteBuf bf = Unpooled.buffer(4 + 4 + data.length);
        bf.writeInt(netMsg.getRequestId());
        bf.writeInt(data.length);
        bf.writeBytes(data);
        // 编码实现
        return bf.array();
    }

    public boolean decode(NettyNetChannel channel, NetMsg msg) {
        if (!init) {
            throw new RuntimeException("CodecService 未被初始化");
        }
        NetMsgTypeEnum msgType = msg.getMsgType();
        if (!Objects.equals(NetMsgTypeEnum.DATA, msgType)) {
            Logger.getLogger(this).error("Codec >> 发送的消息类型不是data类型:{}", msgType);
            return false;
        }
        // 解压缩处理
        // 解加密处理

        // 解码实现 - 请求响应类型消息（可定义、扩展请求类型）
        parseRequestData(msg);

        boolean checkChannelResult = checkChannel(channel, msg);
        if (!checkChannelResult) {
            Logger.getLogger(this).error("NET >> channel 和 msg 校验失败 channel:{},msg:{}", channel, msg);
            return false;
        }

        parseBizData(msg);

        return true;
    }

    /** 处理真实的游戏格式数据 ridLength(byte) + rid(bytes) + bizData */
    private void parseBizData(NetMsg msg) {
        byte[] bizData = msg.getBizData();
        ByteBuf byteBuf = Unpooled.copiedBuffer(bizData);
        byte ridLength = byteBuf.readByte();
        byte[] ridBytes = new byte[ridLength];
        byteBuf.readBytes(ridBytes);
        msg.setRid(new String(ridBytes));
        int dealLength = 1 + ridLength; // 本次读取的字节流长度
        int bizLength = bizData.length - dealLength;
        byte[] newBizData = new byte[bizLength]; // 真正的bizRequest
        byteBuf.readBytes(newBizData);
        msg.setBizData(newBizData);
    }


    /**
     * 检查 channel 和 msg 是否有效
     *
     * @return true:通过
     */
    private boolean checkChannel(NettyNetChannel channel, NetMsg msg) {
        if (Objects.isNull(channel)) {
            Logger.getLogger(this).error("NET >> channel is null ");
            return false;
        }
        if (StringUtils.isEmpty(channel.getRid()) && msg.getMsgCode() != NetConstant.LOGIN_CODE) {
            Logger.getLogger(this).error("NET >> 不是登录消息,但是rid为空,channel:{},msg:{}", channel, msg);
            return false;
        }
        return true;
    }

    /** 处理 请求-响应 模式的特定格式数据 requestId（int）+ msgCode(int) + dataLength(int) + data(bytes) */
    private static void parseRequestData(NetMsg msg) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getData());
        //一个int的requestId
        int requestId = byteBuf.readInt();
        msg.setRequestId(requestId);
        //一个int的msgCode
        int msgCode = byteBuf.readInt();
        msg.setMsgCode(msgCode);
        //带长度的实际数据
        int dataLength = byteBuf.readInt();
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        msg.setBizData(data);
    }

    private CodecService() {
        // 初始化相关资源
    }

    public static CodecService getInstance() {
        return Singleton.INSTANCE.getInstance();
    }


    public enum Singleton {
        INSTANCE;

        private final CodecService codecService;

        Singleton() {
            codecService = new CodecService();
        }

        public CodecService getInstance() {
            return codecService;
        }
    }

}
