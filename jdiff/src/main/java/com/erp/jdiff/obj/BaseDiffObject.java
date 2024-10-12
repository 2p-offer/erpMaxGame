package com.erp.jdiff.obj;

import com.alibaba.fastjson.annotation.JSONField;
import com.erp.jdiff.context.IDiffContext;
import com.erp.jdiff.DiffClassManager;
import com.erp.jdiff.DiffContextManager;
import com.erp.jdiff.log.FieldDiffLog;
import com.erp.jdiff.path.DiffPath;
import com.erp.jdiff.util.DiffObjectUtil;
import com.erp.jdiff.util.DiffPathUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Diff基础类
 *
 * @author wanggang
 * @date 2021/10/26 9:32 下午
 **/
public class BaseDiffObject<F, V> implements IDiffObject {
    private static final Logger logger = LoggerFactory.getLogger(BaseDiffObject.class);
    /**
     * Diff root id
     */
    @Transient
    @JSONField(serialize = false)
    private String rootId = null;
    /**
     * Diff path
     */
    @Transient
    @JSONField(serialize = false)
    private String path;

    public BaseDiffObject() {
    }

    public BaseDiffObject(String rootId, String path) {
        Assert.notNull(rootId, "rootId can not be null!");
        Assert.notNull(path, "path can not be null!");
        this.rootId = rootId;
        this.path = path;
    }

    @Override
    public String getRootId() {
        return rootId;
    }

    @Override
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * <p>返回真实diff数据对象<p/>
     * <p>封装的集合类会重写该方法: DArrayList, DMap等等<p/>
     *
     * @return diff数据对象
     */
    protected Object getDiffData() {
        return this;
    }

    /**
     * <p>更属性节点的path</p>
     * <p>在执行属性get方法时调用</p>
     *
     * @param fieldName 属性的名字
     * @param fieldNode 属性的值(属性对象)
     */
    protected void updatePath(String fieldName, Object fieldNode) {
        if (this.rootId == null || fieldNode == null || StringUtils.isEmpty(this.path)) {
            return;
        }

        if (fieldNode instanceof IDiffObject) {
            IDiffObject diffObject = (IDiffObject) fieldNode;
            if (!StringUtils.isEmpty(diffObject.getRootId()) && diffObject.getRootId().equals(this.rootId)) {
                // rootId相同, 默认已更新过path, 不做任何处理
                return;
            }

            String fieldPath = DiffPathUtil.joinPath(this.path, this.getClass(), fieldName);
            // warning log for multi-reference field
            if (!StringUtils.isEmpty(diffObject.getRootId()) && !diffObject.getRootId().equals(this.rootId)) {
                logger.warn("The new field has different rootId! " +
                                "fieldRootId:{}, newRootId:{}, fieldPath:{}, newFieldPath:{}",
                        diffObject.getRootId(), this.rootId, diffObject.getPath(), fieldPath);
            }

            if (!StringUtils.isEmpty(diffObject.getPath()) && !diffObject.getPath().equals(fieldPath)) {
                logger.warn("The new field has different path! " +
                                "fieldRootId:{}, newRootId:{}, fieldPath:{}, newFieldPath:{}",
                        diffObject.getRootId(), this.rootId, diffObject.getPath(), fieldPath);
            }

            diffObject.setRootId(this.rootId);
            diffObject.setPath(fieldPath);
        }
    }

    /**
     * <p>属性变化记录</p>
     * <p></p>
     * <p>调用时机:</p>
     * <p>1. 在属性set方法时调用</p>
     * <p>2. 集合子节点数据变化时调用</p>
     *
     * @param field        属性名, 或 子节点key
     * @param changedValue 修改后的属性值
     * @param originValue  修改前的属性值
     */
    protected void fieldChanged(F field, V changedValue, V originValue) {
        // 清空原值的diff状态, 避免数据变化生成diff数据. 其他情况在DiffLog中处理.
        if (originValue != changedValue) {
            DiffObjectUtil.clearDiffObjectState(originValue);
        }

        // 无上下文, 数据尚未装配到任何根数据上.
        final Optional<IDiffContext> optionalDiffContext = DiffContextManager.getOptionalDiffContext(this.rootId);
        if (optionalDiffContext.isEmpty() || StringUtils.isEmpty(path)) {
            return;
        }

        // 更新field的diff path
        String fieldName = String.valueOf(field);
        updatePath(fieldName, changedValue);

        // 记录change
        String fieldPath = DiffPathUtil.joinPath(path, this.getClass(), fieldName);
        logger.debug("DiffObject value changed. class = {}, path = {}, origin={}, changed = {}",
                this.getClass().getSimpleName(), fieldPath, originValue, changedValue);
        DiffPath diffPath = new DiffPath(fieldPath);
        if (originValue instanceof IDiffObject || changedValue instanceof IDiffObject) {
            diffPath.setReplaced(originValue != null);
        }

        // 记录diff数据
        final IDiffContext diffContext = optionalDiffContext.get();
        diffContext.valueChanged(diffPath, changedValue);

        // 记录diff回滚Log
        recordDiffLog(diffContext, diffPath, field, originValue, changedValue);
    }

    /**
     * <p>记录日志逻辑<p/>
     * <p>DMap会重写该方法, 使用MapDiffLog记录<p/>
     *
     * @param diffContext  diff上下文
     * @param diffPath     diffPath
     * @param field        key
     * @param originValue  修改前的值
     * @param changedValue 修改后的值, 这里用不到
     */
    protected void recordDiffLog(IDiffContext diffContext, DiffPath diffPath, F field, V originValue, V changedValue) {
        Field targetField = DiffClassManager.getField(this.getClass(), String.valueOf(field));
        if (targetField != null) {
            diffContext.recordDiffLog(diffPath, new FieldDiffLog(this, targetField, originValue));
        } else {
            logger.warn("targetField is null, but filed changes happened: field[{}], originValue[{}], changedValue[{}].", field, originValue, changedValue);
        }
    }
}
