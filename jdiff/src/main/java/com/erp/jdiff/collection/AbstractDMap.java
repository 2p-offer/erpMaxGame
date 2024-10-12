package com.erp.jdiff.collection;

import com.erp.jdiff.context.IDiffContext;
import com.erp.jdiff.log.MapDiffLog;
import com.erp.jdiff.obj.BaseDiffObject;
import com.erp.jdiff.path.DiffPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Diff Map基础抽象类
 *
 * @author wanggang
 * @date 2021/11/6 9:05 下午
 **/
public abstract class AbstractDMap<K, V> extends BaseDiffObject<K, V> implements Map<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDMap.class);

    /**
     * 获得Map对象实例
     *
     * @return Map对象实例
     */
    abstract Map<K, V> getMap();

    @Override
    protected Map<K, V> getDiffData() {
        return getMap();
    }

    /**
     * 重写记录日志, DMap修改使用MapDiffLog记录
     *
     * @param diffContext  diff上下文
     * @param diffPath     diffPath
     * @param field        key
     * @param originValue  修改前的值
     * @param changedValue 修改后的值, 这里用不到
     */
    @Override
    protected void recordDiffLog(IDiffContext diffContext, DiffPath diffPath, K field, V originValue, V changedValue) {
        diffContext.recordDiffLog(diffPath, new MapDiffLog<>(this, field, originValue));
    }

    @Override
    public V put(K key, V value) {
        V originValue = getMap().get(key);
        if (originValue != value) {
            fieldChanged(key, value, originValue);
        }
        return getMap().put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public V remove(Object key) {
        final V removeValue = getMap().remove(key);
        if (removeValue != null) {
            fieldChanged((K) key,
                    null, removeValue);
        }
        return removeValue;
    }

    @Override
    public void clear() {
        getMap().forEach((key, value) -> fieldChanged(key, null, value));
        getMap().clear();
    }

    @Override
    public V get(Object key) {
        V v = getMap().get(key);
        if (key != null) {
            updatePath(String.valueOf(key), v);
        }
        return v;
    }

    /**
     * 数据回滚调用
     *
     * @param key   回滚的key
     * @param value 回滚的value
     */
    public void rollbackTo(K key, V value) {
        if (value == null) {
            getMap().remove(key);
        } else {
            getMap().put(key, value);
            updatePath(String.valueOf(key), value);
        }
    }

    @Override
    public int size() {
        return getMap().size();
    }

    @Override
    public boolean isEmpty() {
        return getMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return getMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return getMap().containsValue(value);
    }

    @Override
    public Set<K> keySet() {
        return new MyKeySet(getMap().keySet());
    }

    @Override
    public Collection<V> values() {
        return new MyValues(getMap().entrySet());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new MyEntrySet(getMap().entrySet());
    }

    @Override
    public String toString() {
        return getMap().toString();
    }

    private final class MyKeySet extends AbstractSet<K> {
        private final Set<K> keySet;

        public MyKeySet(Set<K> keySet) {
            this.keySet = keySet;
        }

        @Nonnull
        @Override
        public Iterator<K> iterator() {
            return new MyKeyIterator(keySet.iterator());
        }

        @Override
        public int size() {
            return keySet.size();
        }
    }

    private final class MyValues extends AbstractCollection<V> {
        private final Set<Map.Entry<K, V>> entrySet;

        public MyValues(Set<Map.Entry<K, V>> entrySet) {
            this.entrySet = entrySet;
        }

        @Nonnull
        @Override
        public Iterator<V> iterator() {
            return new MyValuesIterator(entrySet.iterator());
        }

        @Override
        public int size() {
            return entrySet.size();
        }
    }

    private final class MyEntrySet extends AbstractSet<Map.Entry<K, V>> {
        private final Set<Map.Entry<K, V>> entrySet;

        public MyEntrySet(Set<Map.Entry<K, V>> entrySet) {
            this.entrySet = entrySet;
        }

        @Nonnull
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new MyEntryIterator(entrySet.iterator());
        }

        @Override
        public int size() {
            return entrySet.size();
        }
    }

    private final class MyKeyIterator implements Iterator<K> {
        private final Iterator<K> iterator;
        private K currentKey;

        public MyKeyIterator(Iterator<K> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public K next() {
            currentKey = iterator.next();
            return currentKey;
        }

        @Override
        public void remove() {
            fieldChanged(currentKey, null, get(currentKey));
            iterator.remove();
        }
    }

    private final class MyValuesIterator implements Iterator<V> {
        private final Iterator<Map.Entry<K, V>> iterator;
        private K currentKey;
        private V currentValue;

        public MyValuesIterator(Iterator<Map.Entry<K, V>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public V next() {
            final Entry<K, V> next = iterator.next();
            currentKey = next.getKey();
            currentValue = next.getValue();
            updatePath(String.valueOf(currentKey), currentValue);
            return currentValue;
        }

        @Override
        public void remove() {
            fieldChanged(currentKey, null, currentValue);
            iterator.remove();
        }
    }

    private final class MyEntryIterator implements Iterator<Map.Entry<K, V>> {
        private final Iterator<Map.Entry<K, V>> iterator;
        private K currentKey;
        private V currentValue;

        public MyEntryIterator(Iterator<Map.Entry<K, V>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Map.Entry<K, V> next() {
            final Entry<K, V> next = iterator.next();
            currentKey = next.getKey();
            currentValue = next.getValue();
            updatePath(String.valueOf(currentKey), currentValue);
            return next;
        }

        @Override
        public void remove() {
            fieldChanged(currentKey, null, currentValue);
            iterator.remove();
        }
    }
}
