package com.erp.gameserver.autoconfiguration;

import com.erp.core.serverconfig.ServerStartParams;
import com.erp.core.servernode.GameServerNode;
import com.erp.core.servernode.ServerNodeSupplier;
import com.erp.core.servernode.ServerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class GameServerNodeAutoConfiguration {

    @Bean
    public ServerNodeSupplier<GameServerNode> gameServerNode(ServerStartParams serverStartParams) {
        return () -> {
            GameServerNode serverNode = new GameServerNode();
            serverNode.setServerType(ServerType.GAME);
            serverNode.setIp(serverStartParams.getInnerIp());
            serverNode.setServerId(serverStartParams.getServerId());
            serverNode.setProcessId(UUID.randomUUID().toString());
            //TODO 使用配置的rpcPort
            serverNode.setRpcPort(18013);
            return serverNode;
        };
    }

}
