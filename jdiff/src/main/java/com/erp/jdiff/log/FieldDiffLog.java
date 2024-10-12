package com.erp.jdiff.log;

import com.erp.jdiff.obj.BaseDiffObject;
import com.erp.jdiff.obj.IDiffObject;
import com.erp.jdiff.util.DiffPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 属性和对象 diff操作日志
 *
 * @author wanggang
 * @date 2021/12/1 8:45 下午
 **/
public class FieldDiffLog implements IDiffLog {
    public static final Logger logger = LoggerFactory.getLogger(FieldDiffLog.class);

    private final BaseDiffObject parentObj;

    private final Field targetField;

    private final Object originalValue;

    public FieldDiffLog(@Nonnull BaseDiffObject parentObj, @Nonnull Field modifyField, @Nullable Object originalValue) {
        this.parentObj = Objects.requireNonNull(parentObj, "parentObj is null!");
        this.targetField = Objects.requireNonNull(modifyField, "modifyField is null!");
        this.originalValue = originalValue;
    }

    @Override
    public void undo() {
        try {
            // 恢复originalValue的Path和RootId数据
            if (originalValue instanceof IDiffObject) {
                IDiffObject diffObject = (IDiffObject) originalValue;
                diffObject.setRootId(parentObj.getRootId());
                diffObject.setPath(DiffPathUtil.joinPath(parentObj.getPath(), parentObj.getClass(), targetField.getName()));
            }

            // 通过反射rollback field value.
            targetField.setAccessible(true);
            targetField.set(parentObj, originalValue);
            logger.debug("Field [{}] undo set = {}, path = {}", targetField.getName(), originalValue, parentObj.getPath());
        } catch (IllegalAccessException e) {
            logger.error("undo field error {}|{}: {}", parentObj, targetField, originalValue, e);
        }
    }
}
