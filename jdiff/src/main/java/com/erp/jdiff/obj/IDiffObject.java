package com.erp.jdiff.obj;

/**
 * Diff对象接口
 *
 * @author wanggang
 * @date 2021/10/26 6:07 下午
 **/
public interface IDiffObject {
    /**
     * <p>Diff对象根节点唯一标识.</p>
     * <p>用来标记当前DiffObject属于那个Diff对象</p>
     *
     * @return Root diff object id.
     */
    String getRootId();

    /**
     * <p>Diff对象根节点唯一标识.</p>
     * <p>用来标记当前DiffObject属于那个Diff对象</p>
     *
     * @param rootId 根节点Id
     */
    void setRootId(String rootId);

    /**
     * 获取数据Diff的path路径.
     *
     * @return path.
     */
    String getPath();

    /**
     * 设置数据Diff的path路径.
     *
     * @param path field DiffPath.
     */
    void setPath(String path);
}
