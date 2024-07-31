package com.erp.common.net;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "net")
public class NetConfiguration {

    private int tcpPort;

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    @Bean
    @ConditionalOnMissingBean
    TcpServer tcpServer() {
        return new TcpServer(this);
    }
}
