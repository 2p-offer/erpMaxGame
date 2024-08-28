package com.erp.core.servernode;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.erp.core.constant.CoreConstants;
import com.erp.core.constant.ZkConstants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServiceNodeHelper implements DisposableBean {

    private final CuratorFramework client;
    private final CuratorCache cache;
    /** 服务节点管理本地缓存 */
    private final ServerNodeCache localCache;

    public ServiceNodeHelper(CuratorFramework client) {
        this.client = client;
        localCache = new ServerNodeCache();
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
    public Map<String, GameServerNode> getGameServerNode() {
        return localCache.getServerNodeByType(ServerType.GAME)
                .values()
                .stream()
                .map(GameServerNode.class::cast)
                .collect(Collectors.toMap(GameServerNode::getServerId, Function.identity()));
    }


    /**
     * 获取所有的LogicServer信息
     *
     * @return <serverId:zkValue>
     */
    @Nullable
    public Map<String, LogicServerNode> getLogicServerNode() {
        return localCache.getServerNodeByType(ServerType.LOGIC)
                .values()
                .stream()
                .map(LogicServerNode.class::cast)
                .collect(Collectors.toMap(LogicServerNode::getServerId, Function.identity()));
    }

    /**
     * 注册服务
     *
     * @param node 节点value
     */
    public void registerService(ServerNode node) {
        String path = getServerNodeRootPath() + CoreConstants.SEPARATOR_SLASH + node.getServerId();
        byte[] nodeData = JSON.toJSONBytes(node, JSONWriter.Feature.WriteClassName);
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path, nodeData);
        } catch (Exception e) {
            throw new RuntimeException("服务注册发现中心 >> 注册服务失败:", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        cache.close();
    }
}
