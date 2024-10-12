package com.erp.jdiff.collection;

import com.erp.jdiff.context.IDiffContext;
import com.google.common.collect.ImmutableList;
import com.erp.jdiff.DiffContextManager;
import com.erp.jdiff.log.ListDiffLog;
import com.erp.jdiff.obj.BaseDiffObject;
import com.erp.jdiff.path.DiffPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * 支持Diff功能的ArrayList包装类
 *
 * @author wanggang
 * @date 2021/11/1 11:57 上午
 **/
public class DArrayList<E> extends BaseDiffObject implements List<E> {
    private static final Logger logger = LoggerFactory.getLogger(DArrayList.class);
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final int WARNING_SIZE = 200;

    /**
     * ArrayList数据
     */
    private final ArrayList<E> arrayList;

    public DArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public DArrayList(int initialCapacity) {
        arrayList = new ArrayList<>(initialCapacity);
    }

    @Override
    protected ArrayList<E> getDiffData() {
        return arrayList;
    }

    @Override
    public boolean add(E element) {
        recordDiffLog();
        arrayList.add(element);
        selfChanged();
        return true;
    }

    @Override
    public void add(int index, E element) {
        recordDiffLog();
        arrayList.add(index, element);
        selfChanged();
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> collection) {
        recordDiffLog();
        arrayList.addAll(collection);
        selfChanged();
        return true;
    }

    @Override
    public boolean addAll(int index, @Nonnull Collection<? extends E> collection) {
        recordDiffLog();
        arrayList.addAll(index, collection);
        selfChanged();
        return true;
    }

    @Override
    public E set(int index, E element) {
        recordDiffLog();
        E value = arrayList.set(index, element);
        selfChanged();
        return value;
    }

    @Override
    public E remove(int index) {
        recordDiffLog();
        E value = arrayList.remove(index);
        selfChanged();
        return value;
    }

    @Override
    public boolean remove(Object object) {
        recordDiffLog();
        arrayList.remove(object);
        selfChanged();
        return true;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> collection) {
        recordDiffLog();
        arrayList.removeAll(collection);
        selfChanged();
        return true;
    }

    @Override
    public void clear() {
        recordDiffLog();
        arrayList.clear();
        selfChanged();
    }

    /**
     * 数据回滚调用
     *
     * @param originCollection 回滚的源数据
     */
    public void rollbackTo(Collection<E> originCollection) {
        arrayList.clear();
        arrayList.addAll(originCollection);
    }

    /**
     * ArrayList记录日志
     */
    private void recordDiffLog() {
        final IDiffContext diffContext = DiffContextManager.getDiffContext(getRootId());
        if (diffContext == null) {
            return;
        }
        diffContext.recordDiffLog(new DiffPath(getPath()), new ListDiffLog<>(this, ImmutableList.copyOf(this)));
    }

    /**
     * ArrayList自身数据变更
     */
    void selfChanged() {
        final IDiffContext diffContext = DiffContextManager.getDiffContext(getRootId());
        if (diffContext == null) {
            return;
        }

        if (this.size() >= WARNING_SIZE) {
            logger.warn("size >= {}, [DHashMap] is better than [DArrayList]. rootId = {}, path = {}, value = {}", WARNING_SIZE, getRootId(), getPath(), getDiffData());
        }

        logger.debug("selfChanged. rootId = {}, path = {}, value = {}", getRootId(), getPath(), getDiffData());
        diffContext.valueChanged(new DiffPath(getPath()).setReplaced(true), ImmutableList.copyOf(getDiffData()));
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return arrayList.contains(object);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> collection) {
        return arrayList.containsAll(collection);
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> collection) {
        return arrayList.retainAll(collection);
    }

    @Override
    public E get(int index) {
        return arrayList.get(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator(arrayList.iterator());
    }

    @Override
    public Object[] toArray() {
        return arrayList.toArray();
    }

    @Override
    public <T> T[] toArray(@Nonnull T[] array) {
        return arrayList.toArray(array);
    }

    @Override
    public int indexOf(Object object) {
        return arrayList.indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return arrayList.lastIndexOf(object);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator(arrayList.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(arrayList.listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return arrayList.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return arrayList.toString();
    }

    private class MyIterator implements Iterator<E> {
        private final Iterator<E> iterator;

        private MyIterator(Iterator<E> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            recordDiffLog();
            iterator.remove();
            selfChanged();
        }
    }

    private final class MyListIterator implements ListIterator<E> {
        private final ListIterator<E> iterator;

        private MyListIterator(ListIterator<E> iterator) {
            this.iterator = iterator;
        }

        @Override
        public void remove() {
            recordDiffLog();
            iterator.remove();
            selfChanged();
        }

        @Override
        public void set(E element) {
            recordDiffLog();
            iterator.set(element);
            selfChanged();
        }

        @Override
        public void add(E element) {
            recordDiffLog();
            iterator.add(element);
            selfChanged();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

        @Override
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        @Override
        public E previous() {
            return iterator.previous();
        }

        @Override
        public int nextIndex() {
            return iterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return iterator.previousIndex();
        }
    }
}
