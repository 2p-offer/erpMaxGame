package com.erp.logicserver.autoconfiguration;

import com.erp.core.serverconfig.ServerStartParams;
import com.erp.core.servernode.LogicServerNode;
import com.erp.core.servernode.ServerNodeSupplier;
import com.erp.core.servernode.ServerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class LogicServerNodeAutoConfiguration {

    @Bean
    public ServerNodeSupplier<LogicServerNode> gameServerNode(ServerStartParams serverStartParams) {
        return () -> {
            LogicServerNode serverNode = new LogicServerNode();
            serverNode.setServerType(ServerType.LOGIC);
            serverNode.setIp(serverStartParams.getInnerIp());
            serverNode.setServerId(serverStartParams.getServerId());
            serverNode.setProcessId(UUID.randomUUID().toString());
            //TODO 使用配置的rpcPort
            serverNode.setRpcPort(18033);
            return serverNode;
        };
    }

}
