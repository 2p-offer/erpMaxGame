package com.erp.gameserver.autoconfiguration;

import com.erp.gameserver.net.TcpServer;
import com.erp.net.handler.AbstractNetMsgChannelInboundHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "net")
public class NetAutoConfiguration {

    private int tcpPort;

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    @Bean
    @ConditionalOnMissingBean
    TcpServer tcpServer(AbstractNetMsgChannelInboundHandler bizHandler) {
        return new TcpServer(this, bizHandler);
    }
}
