package com.erp.core.servernode;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.erp.core.constant.CoreConstants;
import com.erp.core.logger.Logger;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.Objects;

public class ServerNodeChildrenCacheListener implements PathChildrenCacheListener {

    private final ServerNodeCache localCache;

    // 定义允许按类型自动反序列化
    private final JSONReader.AutoTypeBeforeHandler autoTypeFilter = JSONReader.autoTypeFilter(ServerNode.class.getPackageName());

    public ServerNodeChildrenCacheListener(ServerNodeCache context) {
        this.localCache = context;
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
        ChildData childData = event.getData();
        PathChildrenCacheEvent.Type type = event.getType();
        if (Objects.isNull(childData)) {
            Logger.getLogger(this).debug("zk 节点发生变化，并且节点信息为空");
            return;
        }
        String path = childData.getPath();
        byte[] data = childData.getData();
        switch (type) {
            case CHILD_ADDED -> {
                if (Objects.isNull(data)) {
                    return;
                }
                ServerNode serverNode = JSON.parseObject(data, ServerNode.class, autoTypeFilter);
                localCache.addNode(serverNode);
            }
            case CHILD_UPDATED -> {
                if (Objects.isNull(data)) {
                    return;
                }
                ServerNode serverNode = JSON.parseObject(data, ServerNode.class, autoTypeFilter);
                localCache.updateNode(serverNode);
            }
            case CHILD_REMOVED -> {
                String serverId = getServerId(path);
                localCache.removeNode(serverId);
            }
            default -> Logger.getLogger(this).debug("zk 节点发生变化，type:{},path:{},data:{}", type, path, new String(data));
        }

    }

    private String getServerId(String path) {
        String[] split = path.split(CoreConstants.SEPARATOR_SLASH);
        return split[split.length - 1];
    }
}
