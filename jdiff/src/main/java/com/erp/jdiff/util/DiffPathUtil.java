package com.erp.jdiff.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import static com.erp.jdiff.DiffConstants.DIFF_SPLIT_FIELD;
import static com.erp.jdiff.DiffConstants.DIFF_SPLIT_NODE;

/**
 * DiffPath工具类
 *
 * @author wanggang
 * @date 2021/10/26 6:45 下午
 **/
public class DiffPathUtil {
    public static String getClassFieldPath(Class<?> clazz, String fieldName) {
        Assert.notNull(clazz, "clazz cannot be null!");
        Assert.isTrue(!StringUtils.isEmpty(fieldName), "fieldName is null or empty");
        return clazz.getSimpleName() + DIFF_SPLIT_FIELD + fieldName;
    }

    public static String joinPath(String parentPath, Class<?> fieldClass, String fieldName) {
        Assert.notNull(parentPath, "parentPath cannot be null!");
        return parentPath + DIFF_SPLIT_NODE + fieldClass.getSimpleName() + DIFF_SPLIT_FIELD + fieldName;
    }
}
