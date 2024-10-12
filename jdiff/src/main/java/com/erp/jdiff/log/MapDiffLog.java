package com.erp.jdiff.log;

import com.erp.jdiff.collection.AbstractDMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Map diff操作日志
 *
 * @author wanggang
 * @date 2021/12/1 8:49 下午
 **/
public class MapDiffLog<K, V> implements IDiffLog {
    public static final Logger logger = LoggerFactory.getLogger(MapDiffLog.class);

    private final AbstractDMap<K, V> mapRef;
    private final K key;
    private final V value;

    public MapDiffLog(AbstractDMap<K, V> mapRef, K key, V value) {
        this.mapRef = mapRef;
        this.key = key;
        this.value = value;
    }

    @Override
    public void undo() {
        logger.debug("MapDiffLog: {} rollback to key[{}]-value[{}]", mapRef, key, value);
        mapRef.rollbackTo(key, value);
    }
}
