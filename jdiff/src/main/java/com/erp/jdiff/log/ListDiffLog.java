package com.erp.jdiff.log;

import com.erp.jdiff.collection.DArrayList;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * List diff操作日志
 *
 * @author wanggang
 * @date 2021/12/1 8:47 下午
 **/
public class ListDiffLog<E> implements IDiffLog {
    public static final Logger logger = LoggerFactory.getLogger(ListDiffLog.class);

    private final DArrayList<E> list;
    private final ImmutableList<E> originalValue;

    public ListDiffLog(DArrayList<E> list, ImmutableList<E> originalValue) {
        this.list = list;
        this.originalValue = originalValue;
    }

    @Override
    public void undo() {
        logger.debug("ListDiffLog: {} rollback to {}", this.list, this.originalValue);
        list.rollbackTo(originalValue);
    }
}
