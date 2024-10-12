package com.erp.jdiff.collection;

import com.erp.jdiff.obj.IDestructible;

import java.util.List;

/**
 * @author Zhang Zhaoyuan
 * @date 2022/4/19 4:28 PM
 */
public class DestructibleArrayList<E> extends DArrayList<E> implements List<E>, IDestructible {

    private volatile boolean destructible;

    @Override
    void selfChanged() {
        if (isDestructible()) {
            return;
        }
        super.selfChanged();
    }

    @Override
    public boolean isDestructible() {
        return destructible;
    }

    @Override
    public void destruct() {
        destructible = true;
    }
}
