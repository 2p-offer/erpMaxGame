package com.erp.jdiff.util;

import com.erp.jdiff.obj.IDiffObject;

/**
 * DiffObject 工具类
 *
 * @author wanggang
 * @date 2022/1/19 6:18 下午
 **/
public class DiffObjectUtil {
    /**
     * 清除DiffObject的状态数据:rootId和path
     *
     * @param object diff对象
     */
    public static void clearDiffObjectState(Object object) {
        if (object instanceof IDiffObject) {
            IDiffObject diffObject = (IDiffObject) object;
            diffObject.setRootId(null);
            diffObject.setPath(null);
        }
    }
}
