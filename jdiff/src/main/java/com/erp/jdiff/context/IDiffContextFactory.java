package com.erp.jdiff.context;

/**
 * @author wanggang
 * @date 2021/11/3 10:18 下午
 **/
public interface IDiffContextFactory {
    /**
     * 创建IDiffContext实例
     *
     * @param diffRootId DiffContext唯一标识
     * @return IDiffContext实例
     */
    IDiffContext create(String diffRootId);
}
