package com.erp.jdiff.collection;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.map.ListOrderedMap;

import java.util.Map;

/**
 * 支持Diff功能的ListOrderedMap包装类
 *
 * @author wanggang
 * @date 2021/11/1 12:10 下午
 **/
public class DListOrderedMap<K, V> extends AbstractDMap<K, V> implements OrderedMap<K, V> {
    /**
     * ListOrderedMap数据
     */
    private final ListOrderedMap<K, V> listOrderedMap = new ListOrderedMap<>();

    @Override
    Map<K, V> getMap() {
        return listOrderedMap;
    }

    @Deprecated
    @Override
    public OrderedMapIterator<K, V> mapIterator() {
        throw new UnsupportedOperationException("mapIterator");
    }

    @Override
    public K firstKey() {
        return listOrderedMap.firstKey();
    }

    @Override
    public K lastKey() {
        return listOrderedMap.lastKey();
    }

    @Override
    public K nextKey(K k) {
        return listOrderedMap.nextKey(k);
    }

    @Override
    public K previousKey(K k) {
        return listOrderedMap.previousKey(k);
    }
}
