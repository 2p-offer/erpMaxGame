package com.erp.jdiff.obj;

/**
 * DiffObject基础类
 *
 * @author wanggang
 * @date 2021/10/26 9:32 下午
 **/
public class DiffObject extends BaseDiffObject<String, Object> {
    public DiffObject() {
        super();
    }

    public DiffObject(String rootId, String path) {
        super(rootId, path);
    }
}
