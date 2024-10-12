package com.erp.jdiff.collection;

import com.erp.jdiff.obj.IDestructible;

/**
 * @author Zhang Zhaoyuan
 * @date 2022/4/19 4:25 PM
 */
public class DestructibleHashMap<K, V> extends DHashMap<K, V> implements IDestructible {

    private volatile boolean destructible;

    @Override
    protected void fieldChanged(K field, V changedValue, V originValue) {
        if (destructible) {
            return;
        }
        super.fieldChanged(field, changedValue, originValue);
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
