package com.erp.jdiff;

import com.erp.jdiff.context.DiffContext;
import com.erp.jdiff.context.IDiffContext;
import com.erp.jdiff.context.IDiffContextFactory;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * DiffContext对象管理器
 *
 * @author wanggang
 * @date 2021/11/3 6:59 下午
 **/
public class DiffContextManager {
    /**
     * Diff Object 管理类
     */
    private static volatile DiffContextManager DIFF_OBJECT_MANAGER = new DiffContextManager(DiffContext::new);

    /**
     * <p>diff数据Map</p>
     */
    private final ConcurrentHashMap<String, IDiffContext> diffContextMap = new ConcurrentHashMap<>();
    /**
     * IDiffContext生成工厂类
     */
    private final IDiffContextFactory diffContextFactory;
    /**
     * 是否正在删除所有上下文
     */
    private final AtomicBoolean removeAll = new AtomicBoolean();

    public DiffContextManager(IDiffContextFactory diffContextFactory) {
        this.diffContextFactory = diffContextFactory;
    }

    /**
     * 初始化DiffObjectManager
     *
     * @param diffContextFactory IDiffContext工厂类
     */
    public static void init(IDiffContextFactory diffContextFactory) {
        Assert.notNull(diffContextFactory, "diffContextFactory can not be null!");
        DIFF_OBJECT_MANAGER = new DiffContextManager(diffContextFactory);
    }

    /**
     * <p>获取diffRootId的上下文对象</p>
     * <p>若上下文对象不存在, 则通过IDiffContextFactory创建</p>
     *
     * @param diffRootId diff对象根节点id
     * @return IDiffContext Diff上下文对象. 若diffRootId为null, 返回null.
     */
    @Nullable
    public static IDiffContext getDiffContext(String diffRootId) {
        return DIFF_OBJECT_MANAGER.getOrCreateDiffContext(diffRootId);
    }

    /**
     * <p>获取diffRootId的上下文对象</p>
     * <p>若上下文对象不存在, 则通过IDiffContextFactory创建</p>
     *
     * @param diffRootId diff对象根节点id
     * @return Optional<IDiffContext> Diff上下文对象. 若diffRootId为null, Optional则为 EMPTY .
     */
    public static Optional<IDiffContext> getOptionalDiffContext(String diffRootId) {
        return Optional.ofNullable(DIFF_OBJECT_MANAGER.getOrCreateDiffContext(diffRootId));
    }

    /**
     * 移除DiffContext
     *
     * @param diffRootId diff对象根节点id
     */
    public static IDiffContext removeDiffContext(String diffRootId) {
        return removeDiffContext(diffRootId, false);
    }

    /**
     * 移除DiffContext同时清空DiffContext中持有的map数据
     *
     * @param diffRootId diff对象根节点id
     * @param isDestroy  是否清空DiffContext中持有的map数据
     * @return null
     */
    public static IDiffContext removeDiffContext(String diffRootId, boolean isDestroy) {
        IDiffContext diffContext = DIFF_OBJECT_MANAGER.remove(diffRootId);
        if (isDestroy && diffContext != null) {
            diffContext.clear();
        }
        return diffContext;
    }

    /**
     * 移除所有DiffContext
     */
    public static ArrayList<IDiffContext> getAndRemoveAllDiffContexts() {
        return DIFF_OBJECT_MANAGER.removeAll();
    }

    @Nullable
    private IDiffContext getOrCreateDiffContext(String diffRootId) {
        if (diffRootId == null) {
            return null;
        }

        while (removeAll.get()) {
            try {
                Thread.currentThread().wait(1);
            } catch (InterruptedException ignored) {
            }
        }
        return diffContextMap.computeIfAbsent(diffRootId,
                key -> diffContextFactory.create(diffRootId));
    }

    private IDiffContext remove(String diffRootId) {
        while (removeAll.get()) {
            try {
                Thread.currentThread().wait(1);
            } catch (InterruptedException ignored) {
            }
        }
        return diffContextMap.remove(diffRootId);
    }

    private ArrayList<IDiffContext> removeAll() {
        removeAll.set(true);
        final ArrayList<IDiffContext> list = new ArrayList<>(diffContextMap.values());
        diffContextMap.clear();
        removeAll.set(false);
        return list;
    }
}
