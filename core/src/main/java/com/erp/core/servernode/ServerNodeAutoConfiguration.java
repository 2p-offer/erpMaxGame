package com.erp.core.servernode;

import com.erp.core.logger.Logger;
import com.erp.core.serverconfig.ServerStartParams;
import com.erp.core.utils.LazyToJsonString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ServerNodeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServerStartParams.class)
    public ServerStartParams buildServerStartParams(
            @Value("${server.ip}") String serverIp,
            @Value("${server.id}") String serverId
    ) {
        ServerStartParams serverStartParams = new ServerStartParams(serverIp, serverId);
        Logger.getLogger(this).info("构建 ServerStartParams, 使用 buildServerStartParams 构建, {}", LazyToJsonString.valueOf(serverStartParams));
        return serverStartParams;
    }

}
