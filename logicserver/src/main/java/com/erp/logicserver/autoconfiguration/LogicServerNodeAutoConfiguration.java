package com.erp.logicserver.autoconfiguration;

import com.erp.core.logger.Logger;
import com.erp.core.serverconfig.ServerStartParams;
import com.erp.core.servernode.LogicServerNode;
import com.erp.core.servernode.ServerNodeSupplier;
import com.erp.core.servernode.ServerType;
import com.erp.core.utils.LazyToJsonString;
import com.erp.logicserver.serverconfig.LogicServerPorts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class LogicServerNodeAutoConfiguration {

    @Bean
    public LogicServerPorts buildServerPorts(
            @Value("${ports.rpc:0}") int rpcPort,
            @Value("${ports.http:0}") int httpPort,
            @Value("${ports.admin:0}") int adminPort
    ) {
        LogicServerPorts ports = new LogicServerPorts(rpcPort, httpPort, adminPort);
        Logger.getLogger(this).info("构建 LogicServerPorts, 使用 buildServerPorts 构建, {}", LazyToJsonString.valueOf(ports));
        return ports;
    }


    @Bean
    public ServerNodeSupplier<LogicServerNode> logicServerNode(ServerStartParams serverStartParams, LogicServerPorts serverPorts) {
        return () -> {
            LogicServerNode serverNode = new LogicServerNode();
            serverNode.setServerType(ServerType.LOGIC);
            serverNode.setIp(serverStartParams.getInnerIp());
            serverNode.setServerId(serverStartParams.getServerId());
            serverNode.setProcessId(UUID.randomUUID().toString());
            serverNode.setRpcPort(serverPorts.getRpcPort());
            return serverNode;
        };
    }

}
