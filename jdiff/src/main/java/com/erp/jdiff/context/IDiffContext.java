package com.erp.jdiff.context;

import com.alibaba.fastjson.JSONObject;
import com.erp.jdiff.log.IDiffLog;
import com.erp.jdiff.path.DiffPath;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Update;

/**
 * diff对象上下文接口
 *
 * @author wanggang
 * @date 2021/11/3 10:06 下午
 **/
public interface IDiffContext {
    /**
     * Diff 数据根节点对象的唯一标识
     *
     * @return 根节点对象唯一标识
     */
    String getDiffRootId();

    /**
     * 记录变化数据到DiffContext
     *
     * @param diffPath     变化数据节点的diff path对象
     * @param changedValue 修改后的值
     */
    void valueChanged(DiffPath diffPath, Object changedValue);

    /**
     * 记录变化数据到DiffContext
     *
     * @param diffPath 变化数据节点的diff path对象
     * @param diffLog  修改前的值
     */
    default void recordDiffLog(DiffPath diffPath, IDiffLog diffLog) {
        // default do nothing
    }

    /**
     * <p>生成diff json 数据</p>
     * <p>同时清除累计的diff记录</p>
     * <p></p>
     *
     * @return diff json object
     */
    JSONObject getDiffJson();

    /**
     * <p>生成update query</p>
     * <p>同时清除累计的update query记录</p>
     *
     * @return 返回修改内容的Update. 若没有修改, 返回null
     */
    Update getUpdateQuery();

    /**
     * 生成序列化好的Document
     * 同时清除累计的update query记录
     *
     * @param converter 项目所使用的MongoConverter
     * @return 返回修改内容的Document. 若没有修改, 返回null
     */
    Document getSerializedDocument(MongoConverter converter);

    /**
     * <p>由 DiffContextManager.removeDiffContext(String diffRootId) 方法替代</p>
     * <p>从DiffContext管理类移除当前DiffContext</p>
     * <p>调用此方法前(后), 请确认update query是否已处理完, 避免数据丢失</p>
     */
    void destroy();

    /**
     * 清空 DiffContext 所持有的diffMap和updateMap
     */
    void clear();
}
