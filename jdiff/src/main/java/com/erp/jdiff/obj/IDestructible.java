package com.erp.jdiff.obj;

/**
 * @author Zhang Zhaoyuan
 * @date 2022/4/15 8:36 PM
 */
public interface IDestructible {

    /**
     * 返回isDestructible标记的状态，默认返回FALSE
     *
     * @return FALSE
     */
    default boolean isDestructible() {
        return false;
    }

    /**
     * 更改isDestructible标记的状态
     */
    void destruct();
}
