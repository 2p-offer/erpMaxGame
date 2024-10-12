package com.erp.jdiff.log;

/**
 * Diff操作日志接口
 *
 * @author wanggang
 * @date 2021/12/1 8:43 下午
 **/
public interface IDiffLog {
    /**
     * 修改回滚
     */
    void undo();
}
