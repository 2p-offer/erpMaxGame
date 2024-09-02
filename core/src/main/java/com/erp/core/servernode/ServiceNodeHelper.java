package com.erp.core.servernode;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.erp.core.constant.CoreConstants;
import com.erp.core.constant.ZkConstants;
import com.erp.core.logger.Logger;
import jakarta.annotation.PreDestroy;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServiceNodeHelper {

    private final CuratorFramework client;
    private CuratorCache cache;
    /** 服务节点管理本地缓存 */
    private final ServerNodeCache localCache = new ServerNodeCache();

    public ServiceNodeHelper(CuratorFramework client) {
        this.client = client;
    }

    @EventListener
    public void onSpringReady(ApplicationReadyEvent event) throws BeansException {
        cache = CuratorCache.build(client, getServerNodeRootPath());
        // 添加监听器
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forPathChildrenCache(getServerNodeRootPath(), client, new ServerNodeChildrenCacheListener(localCache))
                .build();
        cache.listenable().addListener(listener);
        // 启动缓存
        cache.start();
    }

    /** 获取服务器注册发现模块的zk根节点路径 */
    private static String getServerNodeRootPath() {
        return CoreConstants.SEPARATOR_SLASH + ZkConstants.SERVER_NODE_ROOT;
    }

    /**
     * 获取所有的GameServer信息
     *
     * @return <serverId:zkValue>
     */
    @Nullable
    public Map<String, GameServerNode> getGameServerNodeAll() {
        return localCache.getServerNodeByType(ServerType.GAME)
                .values()
                .stream()
                .map(GameServerNode.class::cast)
                .collect(Collectors.toMap(GameServerNode::getServerId, Function.identity()));
    }

    /**
     * 获取单个GameServer信息
     *
     * @return zk 服务节点信息
     */
    @Nullable
    public GameServerNode getGameServerNode(String serverId) {
        return localCache.getServerNodeByType(ServerType.GAME)
                .values()
                .stream()
                .map(GameServerNode.class::cast)
                .collect(Collectors.toMap(GameServerNode::getServerId, Function.identity()))
                .get(serverId);
    }


    /**
     * 获取所有的LogicServer信息
     *
     * @return <serverId:zkValue>
     */
    @Nullable
    public Map<String, LogicServerNode> getLogicServerNodeAll() {
        return localCache.getServerNodeByType(ServerType.LOGIC)
                .values()
                .stream()
                .map(LogicServerNode.class::cast)
                .collect(Collectors.toMap(LogicServerNode::getServerId, Function.identity()));
    }

    /**
     * 获取单个LogicServer信息
     */
    @Nullable
    public LogicServerNode getLogicServerNode(String serverId) {
        return localCache.getServerNodeByType(ServerType.LOGIC)
                .values()
                .stream()
                .map(LogicServerNode.class::cast)
                .collect(Collectors.toMap(LogicServerNode::getServerId, Function.identity()))
                .get(serverId);
    }

    /**
     * 注册服务
     *
     * @param node 节点value
     */
    public void registerService(ServerNode node) {
        String localServerId = localCache.getServerId();
        String serverId = node.getServerId();
        if (StringUtils.isNotEmpty(localServerId)) {
            throw new RuntimeException("服务重复注册 old: " + localServerId + ",new: " + serverId);
        }
        String path = getServerNodeRootPath() + CoreConstants.SEPARATOR_SLASH + serverId;
        byte[] nodeData = JSON.toJSONBytes(node, JSONWriter.Feature.WriteClassName);
        try {
            Stat stat = client.checkExists().forPath(path);
            if (stat != null) {
                Logger.getLogger(this).warn("服务注册发现中心 >> 服务注册时,本节点已经存在:{}", node);
                client.delete().forPath(path);
            }
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path, nodeData);
            localCache.setServerId(serverId);
        } catch (Exception e) {
            throw new RuntimeException("服务注册发现中心 >> 注册服务失败:", e);
        }
        Logger.getLogger(this).info("服务注册发现中心 >> 服务注册完成:{}", node);
    }

    @PreDestroy
    public void destroy() throws Exception {
        String path = getServerNodeRootPath() + CoreConstants.SEPARATOR_SLASH + localCache.getServerId();
        client.delete().forPath(path);
        cache.close();
        client.close();
    }

}
