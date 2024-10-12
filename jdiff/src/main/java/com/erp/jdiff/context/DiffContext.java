package com.erp.jdiff.context;

import com.alibaba.fastjson.JSONObject;
import com.erp.jdiff.DiffClassManager;
import com.erp.jdiff.DiffContextManager;
import com.erp.jdiff.obj.IDiffObject;
import com.erp.jdiff.path.DiffPath;
import com.erp.jdiff.util.DiffJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.UpdateMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static com.erp.jdiff.DiffConstants.DIFF_JSON_KEY_DIFF;
import static com.erp.jdiff.DiffConstants.DIFF_JSON_KEY_REPLACE;

/**
 * <p>Diff上下文类</p>
 * <p>- 记录一个diff对象的修改</p>
 * <p>- 生成diff数据</p>
 * <p>- 生成Update</p>
 *
 * @author wanggang
 * @date 2021/11/3 10:16 下午
 **/
public class DiffContext implements IDiffContext {
    public static final Logger logger = LoggerFactory.getLogger(DiffContext.class);

    private final String diffRootId;
    private final Map<DiffPath.DiffNode, Map<DiffPath, ChangeValue>> diffMap = new HashMap<>();
    private final Map<DiffPath.DiffNode, Map<DiffPath, ChangeValue>> updateMap = new ConcurrentHashMap<>();
    /**
     * diff json开关, 默认开启
     */
    private final boolean diffJsonOn;

    public DiffContext(String diffRootId) {
        this.diffRootId = diffRootId;

        this.diffJsonOn = true;
    }

    public DiffContext(String diffRootId, boolean diffJsonOn) {
        this.diffRootId = diffRootId;
        this.diffJsonOn = diffJsonOn;
    }

    @Override
    public String getDiffRootId() {
        return diffRootId;
    }

    @Override
    public void valueChanged(DiffPath diffPath, Object changedValue) {
        if (diffJsonOn) {
            addChangeToMap(diffMap, diffPath, changedValue, HashMap::new);
        }

        addChangeToMap(updateMap, diffPath, changedValue, ConcurrentHashMap::new);
    }

    @Nonnull
    @Override
    public JSONObject getDiffJson() {
        // 完整diff json, 包括 diff 和 replace 两部分.
        // 客户端需要先处理replace的数据, 再处理diff数据\
        final JSONObject diffJsonObject = new JSONObject();
        // diff 数据
        final JSONObject diffData = new JSONObject();
        // replace 数据, 记录了哪些节点是被替换过的,
        // 对应的值都是 {@link com.topjoy.jdiff.DiffConstants#REMOVE_SYMBOL}
        final JSONObject replaceData = new JSONObject();

        if (diffJsonOn) {
            diffMap.values().forEach(pathChangeValueMap -> pathChangeValueMap.forEach((diffPath, obj) -> {
                if (DiffClassManager.isDiffIgnore(diffPath)) {
                    return;
                }

                // 生成到diff json. 若值为null, 生成到replace json
                if (obj.value != null) {
                    DiffJsonUtil.getDiffJsonObject(diffData, diffPath, obj.value);
                }

                // 生成到replace json
                if (obj.value == null || diffPath.isReplaced()) {
                    DiffJsonUtil.getDiffJsonObject(replaceData, diffPath, null);
                }
            }));
            diffMap.clear();
        }

        diffJsonObject.put(DIFF_JSON_KEY_DIFF, diffData);
        diffJsonObject.put(DIFF_JSON_KEY_REPLACE, replaceData);
        return diffJsonObject;
    }

    @Nullable
    @Override
    public Update getUpdateQuery() {
        AtomicBoolean isChanged = new AtomicBoolean(false);
        final Update updateQuery = new Update();
        updateMap.values().stream()
                .flatMap(updateChangeMap -> {
                    // 按照顶层Path路径(模块)校验数据, 较少不同模块之间的无效比较次数
                    final Iterator<Map.Entry<DiffPath, ChangeValue>> iterator = updateChangeMap.entrySet().iterator();
                    HashMap<DiffPath, ChangeValue> intermediaryMap = new HashMap<>(updateMap.size());
                    while (iterator.hasNext()) {
                        Map.Entry<DiffPath, ChangeValue> entry = iterator.next();
                        iterator.remove();
                        DiffPath diffPath = entry.getKey();
                        ChangeValue changedValue = entry.getValue();
                        verifyMap(intermediaryMap, diffPath, changedValue);
                    }
                    return intermediaryMap.entrySet().stream();
                })
                .forEach(entry -> {
                    final DiffPath diffPath = entry.getKey();
                    if (DiffClassManager.isDbIgnore(diffPath)) {
                        return;
                    }

                    String updatePath = diffPath.getUpdatePath();
                    if (StringUtils.isEmpty(updatePath)) {
                        return;
                    }

                    isChanged.set(true);
                    final ChangeValue objValue = entry.getValue();
                    if (objValue.value == null) {
                        updateQuery.unset(updatePath);
                    } else {
                        updateQuery.set(updatePath, objValue.value);
                    }
                });

        return isChanged.get() ? updateQuery : null;
    }

    /**
     * 生成序列化好的Document 同时清除累计的update query记录
     *
     * @param converter 项目所使用的MongoConverter
     * @return 序列化好的Document
     */
    @Nullable
    @Override
    public Document getSerializedDocument(MongoConverter converter) {
        Assert.notNull(converter, "MongoConverter can not be null.");
        final Update updateQuery = getUpdateQuery();
        if (updateQuery == null) {
            return null;
        }

        UpdateMapper updateMapper = new UpdateMapper(converter);
        MongoPersistentEntity<?> entity = converter.getMappingContext().getPersistentEntity(IDiffObject.class);
        return updateMapper.getMappedObject(updateQuery.getUpdateObject(), entity);
    }

    @Override
    public void destroy() {
        DiffContextManager.removeDiffContext(diffRootId);
    }

    @Override
    public void clear() {
        diffMap.clear();
        updateMap.clear();
    }

    protected void addChangeToMap(Map<DiffPath.DiffNode, Map<DiffPath, ChangeValue>> diffMap, DiffPath diffPath, Object changedValue, Supplier<Map<DiffPath, ChangeValue>> mapSupplier) {
        if (diffPath.getNodeList().size() < 2) {
            // DiffPath至少由root节点和子节点组成, NodeList.size一定是大于等于2的.
            // 逻辑上不会执行到这里. 仅做异常处理, 避免下面代码出现索引越界异常.
            logger.error("Error diffPath! NodeList.size < 2. diffPath:{}", diffPath);
            return;
        }
        // 索引 0 位置是root节点
        // 索引 1 位置是第一层子节点, 业务上可以理解为各个模块的根节点.
        // 目前只使用第一层子节点分组. 如果再加深, 需要判断层级深度, 因为根节点的属性可以没有子节点, 深度只是2.
        final DiffPath.DiffNode moduleRootNode = diffPath.getNodeList().get(1);
        final Map<DiffPath, ChangeValue> pathChangeValueMap = diffMap.compute(moduleRootNode, (node, map) -> map != null ? map : mapSupplier.get());
        final Iterator<DiffPath> iterator = pathChangeValueMap.keySet().iterator();
        while (iterator.hasNext()) {
            DiffPath nodePath = iterator.next();
            if (nodePath.isParentOf(diffPath)) {
                logger.debug("sub path [{}] ignored because of [{}] already added.", diffPath, nodePath);
                return;
            }

            // 检测已经修改的FieldDiffMap中是否有当前Path的子Path，如果有，可以优化掉，提升后期转json的效率
            if (nodePath.isChildOf(diffPath)) {
                iterator.remove();
            }
        }

        logger.debug("change path [{}] to value [{}].", diffPath.getDiffPath(), changedValue);
        pathChangeValueMap.compute(diffPath, (path, obj) -> {
            // 若有修改值, 合并remove状态
            if (obj != null) {
                diffPath.setReplaced(diffPath.isReplaced() || obj.removed);
            }

            return new ChangeValue(diffPath.isReplaced(), changedValue);
        });
    }

    /**
     * 将updateMap中的数据验证并加入intermediaryMap中
     *
     * @param diffMap      待添加数据的map
     * @param diffPath     diffPath
     * @param changedValue changedValue
     */
    protected void verifyMap(Map<DiffPath, ChangeValue> diffMap, DiffPath diffPath, ChangeValue changedValue) {
        final Iterator<DiffPath> iterator = diffMap.keySet().iterator();
        while (iterator.hasNext()) {
            DiffPath nodePath = iterator.next();
            if (nodePath.isParentOf(diffPath)) {
                return;
            }

            // 检测已经修改的FieldDiffMap中是否有当前Path的子Path，如果有，可以优化掉，提升后期转json的效率
            if (nodePath.isChildOf(diffPath)) {
                iterator.remove();
            }
        }
        logger.debug("change path [{}] to value [{}].", diffPath.getDiffPath(), changedValue);
        diffMap.putIfAbsent(diffPath, changedValue);
    }

    protected static final class ChangeValue {
        private final boolean removed;
        private final Object value;

        ChangeValue(boolean removed, Object value) {
            this.removed = removed;
            this.value = value;
        }

        public Object getValue() {
            return value;
        }
    }
}
