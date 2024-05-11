package com.erp.core.zkconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZkConfigTest {


    @Value("test.a")
    private String nodeData;

    public String getNodeData() {
        return nodeData;
    }

    public void setNodeData(String nodeData) {
        this.nodeData = nodeData;
    }
}
