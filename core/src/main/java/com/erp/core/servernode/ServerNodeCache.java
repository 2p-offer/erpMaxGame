package com.erp.core.servernode;

import com.alibaba.fastjson2.JSONObject;
import com.erp.core.logger.Logger;
import org.apache.zookeeper.common.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** 服务节点信息本地缓存 */
public class ServerNodeCache {

    /** 本服务serverId */
    private String serverId;
    /** serverType : <serverId : serverNode> */
    private final Map<ServerType, Map<String, ServerNode>> nodeData = new EnumMap<>(ServerType.class);

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    /** 添加一个节点 */
    public void addNode(ServerNode node) {
        if (Objects.isNull(node)) {
            Logger.getLogger(this).error("SERVER NODE >> 添加节点，但是目标节点为空");
            return;
        }
        ServerType serverType = node.getServerType();
        Map<String, ServerNode> serverNodeMap = nodeData.computeIfAbsent(serverType, type -> new ConcurrentHashMap<>());
        ServerNode oldValue = serverNodeMap.put(node.getServerId(), node);
        if (Objects.nonNull(oldValue)) {
            Logger.getLogger(this).error("SERVER NODE >> 新添加了节点，但是旧节点已经存在,new:{},old:{}", node, oldValue);
        }
        Logger.getLogger(this).warn("SERVER NODE >> 添加节点:{}", node);
    }

    /** 更新一个节点 */
    public void updateNode(ServerNode node) {
        if (Objects.isNull(node)) {
            Logger.getLogger(this).error("SERVER NODE >> 添加节点，但是目标节点为空");
            return;
        }
        ServerType serverType = node.getServerType();
        Map<String, ServerNode> serverNodeMap = nodeData.computeIfAbsent(serverType, type -> new ConcurrentHashMap<>());
        serverNodeMap.put(node.getServerId(), node);
        Logger.getLogger(this).warn("SERVER NODE >> 更新节点:{}", node);
    }

    /** 删除一个节点 */
    public void removeNode(String serverId) {
        if (StringUtils.isEmpty(serverId)) {
            Logger.getLogger(this).error("SERVER NODE >> 删除节点，但是目标节点key为空");
            return;
        }
        long count = nodeData.values()
                .stream()
                .filter(nodeMap -> nodeMap.containsKey(serverId))
                .count();
        if (count != 1) {
            Logger.getLogger(this).error("SERVER NODE >> 删除了旧节点，但是旧节点不是存在且仅存在一个,nodeMap:{},removeServerId:{}", JSONObject.toJSONString(nodeData), serverId);
        }
        ServerNode serverNode = getServerNodeAny(serverId);
        if (Objects.isNull(serverNode)) {
            Logger.getLogger(this).error("SERVER NODE >> 删除旧节点，但是旧节点不存在,nodeMap:{},removeServerId:{}", JSONObject.toJSONString(nodeData), serverId);
            return;
        }
        nodeData.computeIfAbsent(serverNode.getServerType(), type -> new ConcurrentHashMap<>()).remove(serverId);
        Logger.getLogger(this).warn("SERVER NODE >> 移除节点:{}", serverId);
    }

    @Nullable
    private ServerNode getServerNodeAny(String serverId) {
        return nodeData.values()
                .stream()
                .filter(nodeMap -> nodeMap.containsKey(serverId))
                .findAny()
                .orElse(Collections.emptyMap())
                .get(serverId);
    }

    /** 获取某个服务类型的所有节点信息 */
    public Map<String, ServerNode> getServerNodeByType(ServerType serverType) {
        return nodeData.getOrDefault(serverType, new HashMap<>());
    }
}
