package com.erp.jdiff.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.UnmodifiableIterator;
import com.erp.jdiff.DiffConstants;
import com.erp.jdiff.path.DiffPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.Objects;

/**
 * 获得Diff Json的工具类
 *
 * @author wanggang
 * @date 2021/11/8 4:32 下午
 **/
public class DiffJsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(DiffJsonUtil.class);

    /**
     * <p>获取Diff Json数据, 生成到 parentObj 中</p>
     * <p>默认会去掉DiffPath的根节点, 直接将更节点下的数据生成到 parentObj 中</p>
     *
     * @param parentObj Json数据父节点
     * @param diffPath  DiffPath路径
     * @param objValue  对象数值
     */
    public static void getDiffJsonObject(JSONObject parentObj, DiffPath diffPath, Object objValue) {
        Assert.notNull(parentObj, "Parent json object cannot be null!");
        Assert.notNull(diffPath, "DiffPath cannot be null!");

        // 去掉根节点, 根节点不生成到Diff Json中
        final UnmodifiableIterator<DiffPath.DiffNode> nodeIterator = diffPath.getNodeList().iterator();
        if (nodeIterator.hasNext()) {
            nodeIterator.next();
        }

        // 用特殊字符代替值为null的objValue
        objValue = Objects.requireNonNullElse(objValue, DiffConstants.REMOVE_SYMBOL);

        getDiffJsonObject(parentObj, nodeIterator, objValue);

        if (logger.isDebugEnabled()) {
            logger.debug(parentObj.toJSONString());
        }
    }

    private static JSONObject getDiffJsonObject(JSONObject parentObj, Iterator<DiffPath.DiffNode> nodeIterator, Object objValue) {
        if (!nodeIterator.hasNext()) {
            // 理论上应该走不到这里
            return parentObj;
        }

        final DiffPath.DiffNode node = nodeIterator.next();
        String fieldName = node.getFieldName();
        if (!nodeIterator.hasNext()) {
            parentObj.put(fieldName, objValue);
            return parentObj;
        }

        JSONObject currObj;
        if (parentObj.containsKey(fieldName)) {
            currObj = parentObj.getJSONObject(fieldName);
        } else {
            currObj = new JSONObject();
        }

        JSONObject childObj = getDiffJsonObject(currObj, nodeIterator, objValue);
        if (childObj != null) {
            parentObj.put(fieldName, childObj);
        }
        return parentObj;
    }
}
