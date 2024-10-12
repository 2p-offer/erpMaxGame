package com.erp.jdiff.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持Diff功能的HashMap包装类
 *
 * @author wanggang
 * @date 2021/11/1 12:01 下午
 **/
public class DHashMap<K, V> extends AbstractDMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    /**
     * HashMap数据
     */
    private final HashMap<K, V> hashMap;

    public DHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public DHashMap(int initialCapacity) {
        hashMap = new HashMap<>(initialCapacity);
    }

    @Override
    protected Map<K, V> getMap() {
        return hashMap;
    }
}
