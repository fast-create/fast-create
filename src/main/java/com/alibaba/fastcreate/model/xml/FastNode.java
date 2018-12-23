package com.alibaba.fastcreate.model.xml;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import lombok.Data;

@Data
public class FastNode {

    /**
     * 惟一标志
     */
    private String nodeId;

    /**
     * 字段名
     */
    private String nodeName;

    private String nodeValue;

    private String nodeType;

    /**
     * 标签名
     */
    private String nodeLocalName;

    private String parentNodeId;

    private FastNode rootNode;

    private List<String> childNodes = Lists.newArrayList();

    private FastAttributeMap attributeMap;

    private int nodeLevel;

    public boolean isRootNode() {
        return this.parentNodeId == null;
    }

    public boolean hasChild() {
        return this.childNodes != null && !this.childNodes.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FastNode fastNode = (FastNode) o;
        return nodeLevel == fastNode.nodeLevel &&
            nodeName.equals(fastNode.nodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeName, nodeLevel);
    }

    public String getClassName() {
        return this.nodeName.substring(0, 1).toUpperCase() + this.nodeName.substring(1);
    }
}
