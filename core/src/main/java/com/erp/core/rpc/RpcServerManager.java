package com.erp.core.rpc;

import com.erp.core.bean.BeanManager;
import com.erp.core.exception.LogicException;
import com.erp.core.exception.ResCodeEnum;
import com.erp.core.logger.Logger;
import com.erp.core.serverconfig.ServerPorts;
import com.erp.core.serverconfig.ServerStartParams;
import com.erp.core.servernode.ServerNode;
import com.erp.core.servernode.ServerType;
import com.erp.core.servernode.ServiceNodeHelper;
import com.erp.rpc.game.hello.HelloGrpc;
import com.erp.rpc.game.hello.HelloWorldApi;
import io.grpc.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RpcServerManager {

    private final ServiceNodeHelper nodeHelper;
    private final ServerStartParams serverStartParams;
    private final ServerPorts serverPorts;
    private final Map<String, RpcChannel> channels = new ConcurrentHashMap<>();
    private Server server;

    public RpcServerManager(ServiceNodeHelper nodeHelper, ServerStartParams serverStartParams, ServerPorts serverPorts) {
        this.nodeHelper = nodeHelper;
        this.serverStartParams = serverStartParams;
        this.serverPorts = serverPorts;
    }

    @EventListener
    public void connect(RpcServerConnectEvent event) {
        ServerNode serverNode = event.getServerNode();
        String serverId = serverNode.getServerId();
        if (StringUtils.equals(serverStartParams.getServerId(), serverId)) {
            return;
        }
        //TODO 不应该是每个服务都要连接，过滤需要连接的服务。 比如一个逻辑服A负责8个游戏服 section 1-8。那只有这8个游戏服所在的进程才需要连接A逻辑服
        try {
            createNewChannel(serverNode);
        } catch (Throwable t) {
            Logger.getLogger(this).warn("RPC Server >> 新连接建立失败,考虑目标服务是否启动停止操作过快,targetNode:{}", serverNode);
        }
    }

    @EventListener
    public void remove(RpcServerRemoveEvent event) {
        String serverId = event.getServerId();
        channels.remove(serverId);
        Logger.getLogger(this).warn("RPC Server >> rpc channel remove:{}", serverId);
    }

    /**
     * 根据服务节点信息创建一个新的grpc连接
     *
     * @param serverNode 目标服务信息
     * @return rpc连接
     */
    private RpcChannel createNewChannel(ServerNode serverNode) {
        String serverId = serverNode.getServerId();
        ManagedChannel channel = connect(serverNode);
        RpcChannel rpcChannel = new RpcChannel(channel, serverId);
        RpcChannel oldChannel = channels.put(serverId, rpcChannel);
        if (Objects.nonNull(oldChannel)) {
            Logger.getLogger(this).warn("RPC Server >> rpc channel 连接被更新");
            oldChannel.getGrpcChannel().shutdown();
        }
        Logger.getLogger(this).info("RPC Server >> rpc channel add:{}", serverId);
        return rpcChannel;
    }


    /**
     * 启动Rpc服务
     */
    public void start() {
        try {
            Map<String, Object> rpcBean = BeanManager.getBeanWithAnnotation(RpcService.class);
            ServerBuilder<?> serverBuilder = ServerBuilder.forPort(serverPorts.getRpcPort());
            ServerBuilder.forPort(serverPorts.getRpcPort());
            rpcBean.values()
                    .stream()
                    .map(BindableService.class::cast)
                    .forEach(serverBuilder::addService);
            server = serverBuilder.build();
            server.start();
            Logger.getLogger(this).info("RPC Server >> rpc server 端口暴露完成:{}", serverPorts.getRpcPort());
        } catch (Exception e) {
            throw new RuntimeException("RPC Server >> rpc server 端口启动失败", e);
        }
    }

    public void stop() {
        Logger.getLogger(this).info("开始关闭Rpc server");
        for (RpcChannel rpcChannel : channels.values()) {
            rpcChannel.getGrpcChannel().shutdown();
            Logger.getLogger(this).info("与 {} rpc连接断开...", rpcChannel.getServerId());
        }
        server.shutdown();
    }

    /**
     * 与某个服务节点建立RPC连接
     *
     * @param node 目标节点
     * @return grpc channel
     */
    public ManagedChannel connect(ServerNode node) {
        //TODO 调整grpc connect的构建参数
        ManagedChannel channel = ManagedChannelBuilder.forAddress(node.getIp(), node.getRpcPort())
                .usePlaintext()
                .build();
        HelloWorldApi.HelloRequest request = HelloWorldApi.HelloRequest.newBuilder()
                .setData("Channel Init For Connect !")
                .build();
        HelloWorldApi.HelloResponse response = HelloGrpc.newBlockingStub(channel).sayHello(request);
        Logger.getLogger(this).info("RPC Server >> rpc connect完成:{}", node);
        return channel;
    }

    public RpcChannel getOrInitChannel(ServerNode node) {
        return getOrInitChannel(node.getServerType(), node.getServerId());
    }

    /**
     * 获取目标服务的rpc连接，如果连接没有创建则创建并返回
     *
     * @param serverType 目标服务类型
     * @param serverId   目标服务id
     * @return rpc连接
     */
    public RpcChannel getOrInitChannel(ServerType serverType, String serverId) {
        RpcChannel rpcChannel = channels.get(serverId);
        if (Objects.nonNull(rpcChannel)) {
            return rpcChannel;
        }
        ServerNode node = switch (serverType) {
            case GAME -> nodeHelper.getGameServerNode(serverId);
            case LOGIC -> nodeHelper.getLogicServerNode(serverId);
            case GLOBAL ->
                    throw new LogicException(ResCodeEnum.ERROR_CODE_SERVER_NODE_NOT_FOUND, "targetType:{},serverId:{}", serverType, serverId);
        };
        if (Objects.isNull(node)) {
            throw new LogicException(ResCodeEnum.ERROR_CODE_SERVER_NODE_NOT_FOUND, "根据type和id找不到对应的服务器节点.targetType:{},serverId:{}", serverType, serverId);
        }
        return createNewChannel(node);
    }

}
