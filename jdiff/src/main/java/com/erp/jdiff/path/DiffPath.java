package com.erp.jdiff.path;

import com.google.common.collect.ImmutableList;
import com.erp.jdiff.DiffConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Diff路径包装类
 * 用来生成Diff数据和Update Query
 *
 * @author wanggang
 */
public class DiffPath {
    /**
     * 当前DiffNode的所有节点信息
     */
    private final ImmutableList<DiffNode> nodeList;
    /**
     * 完整Diff Path路径
     */
    private final String diffPath;
    /**
     * 根节点Class name, 用于Update
     */
    private final String rootClassName;
    /**
     * 完整Update Path路径
     */
    private final String updatePath;
    /**
     * 是否节点是replace节点
     */
    private boolean replaced = false;

    public DiffPath(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path cannot be null!");
        }

        var nodePaths = StringUtils.split(path, DiffConstants.DIFF_SPLIT_NODE);
        List<DiffNode> list = new ArrayList<>();
        StringBuilder strPath = new StringBuilder();
        for (int i = 0; i < nodePaths.length; i++) {
            final String nodePath = nodePaths[i];
            final DiffNode node = new DiffNode(nodePath);
            list.add(node);
            if (i > 0) {
                strPath.append(node.fieldName).append(".");
            }
        }

        // diff path
        this.diffPath = path;
        // update query path
        this.updatePath = strPath.length() > 0 ? strPath.substring(0, strPath.length() - 1) : "";
        // 根节点Class name
        this.rootClassName = list.get(0).getClassName();
        // 持有不可变List
        this.nodeList = ImmutableList.copyOf(list);
    }

    public boolean isParentOf(DiffPath path) {
        if (path.getNodeList().size() <= this.getNodeList().size()) {
            return false;
        }

        for (int i = 0; i < this.nodeList.size(); i++) {
            if (!this.nodeList.get(i).equals(path.getNodeList().get(i))) {
                return false;
            }
        }

        return true;
    }

    public boolean isChildOf(DiffPath path) {
        if (path.getNodeList().size() >= this.getNodeList().size()) {
            return false;
        }

        for (int i = 0; i < path.nodeList.size(); i++) {
            if (!this.nodeList.get(i).equals(path.getNodeList().get(i))) {
                return false;
            }
        }

        return true;
    }

    public ImmutableList<DiffNode> getNodeList() {
        return nodeList;
    }

    public String getDiffPath() {
        return diffPath;
    }

    public String getRootClassName() {
        return rootClassName;
    }

    public String getUpdatePath() {
        return updatePath;
    }

    public boolean isReplaced() {
        return replaced;
    }

    public DiffPath setReplaced(boolean replaced) {
        this.replaced = replaced;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != DiffPath.class) {
            return false;
        }

        if (StringUtils.isEmpty(diffPath)) {
            return false;
        }

        final DiffPath path = (DiffPath) obj;
        return diffPath.equals(path.getDiffPath());
    }

    @Override
    public int hashCode() {
        return this.diffPath.hashCode();
    }

    @Override
    public String toString() {
        return "DiffPath{" +
                "nodeList=" + nodeList +
                ", diffPathStr='" + diffPath + '\'' +
                '}';
    }

    public static class DiffNode {
        /**
         * 类path: 类名
         */
        private final String className;
        /**
         * 属性path: 属性名
         */
        private final String fieldName;
        /**
         * <p>完整path</p>
         * <p>例:</p>
         * <p>类名|属性名</p>
         * <p>或</p>
         * <p>类名|属性名#...#类名|属性名</p>
         */
        private final String nodePath;

        DiffNode(String nodePath) {
            var array = StringUtils.split(nodePath, DiffConstants.DIFF_SPLIT_FIELD);
            if (array == null || array.length < 2) {
                throw new IllegalArgumentException("Error clzFieldPath! nodePath = " + nodePath);
            }
            this.className = array[0];
            this.fieldName = array[1];
            this.nodePath = nodePath;
        }

        public String getClassName() {
            return className;
        }

        public String getFieldName() {
            return fieldName;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != DiffNode.class) {
                return false;
            }
            return obj.toString().equals(this.toString());
        }

        @Override
        public int hashCode() {
            return this.nodePath.hashCode();
        }

        @Override
        public String toString() {
            return this.nodePath;
        }
    }
}
