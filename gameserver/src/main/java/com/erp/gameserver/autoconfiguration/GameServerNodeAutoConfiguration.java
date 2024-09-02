package com.erp.gameserver.autoconfiguration;

import com.erp.core.logger.Logger;
import com.erp.core.serverconfig.ServerPorts;
import com.erp.core.serverconfig.ServerStartParams;
import com.erp.core.servernode.GameServerNode;
import com.erp.core.servernode.ServerNodeSupplier;
import com.erp.core.servernode.ServerType;
import com.erp.core.utils.LazyToJsonString;
import com.erp.gameserver.serverconfig.GameServerPorts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class GameServerNodeAutoConfiguration {

    @Bean
    public GameServerPorts buildServerPorts(
            @Value("${ports.rpc:0}") int rpcPort,
            @Value("${ports.http:0}") int httpPort,
            @Value("${ports.admin:0}") int adminPort,
            @Value("${ports.tcp:0}") int tcpPort
    ) {
        GameServerPorts ports = new GameServerPorts(rpcPort, httpPort, adminPort, tcpPort);
        Logger.getLogger(this).info("构建 GameServerPorts, 使用 buildServerPorts 构建, {}", LazyToJsonString.valueOf(ports));
        return ports;
    }

    @Bean
    public ServerNodeSupplier<GameServerNode> gameServerNode(ServerStartParams serverStartParams, ServerPorts serverPorts) {
        return () -> {
            GameServerNode serverNode = new GameServerNode();
            serverNode.setServerType(ServerType.GAME);
            serverNode.setIp(serverStartParams.getInnerIp());
            serverNode.setServerId(serverStartParams.getServerId());
            serverNode.setProcessId(UUID.randomUUID().toString());
            serverNode.setRpcPort(serverPorts.getRpcPort());
            return serverNode;
        };
    }

}
