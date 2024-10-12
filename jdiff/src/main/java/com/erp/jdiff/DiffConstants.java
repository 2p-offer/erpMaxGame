package com.erp.jdiff;

/**
 * @author wanggang
 * @date 2021/11/3 10:54 下午
 **/
public class DiffConstants {
    /**
     * 类与属性间的分隔符
     */
    public static final String REMOVE_SYMBOL = "$NULL";
    /**
     * diff数据节点key
     */
    public static final String DIFF_JSON_KEY_DIFF = "d";
    /**
     * replace数据节点key
     */
    public static final String DIFF_JSON_KEY_REPLACE = "r";
    /**
     * diff警告日志标识
     */
    public static final String DIFF_WARN_TAG = "[diff-warn]";
    /**
     * 类与属性间的分隔符
     */
    public static String DIFF_SPLIT_FIELD = "|";
    /**
     * 父节点与子节点path的分隔符
     */
    public static String DIFF_SPLIT_NODE = "#";
}
