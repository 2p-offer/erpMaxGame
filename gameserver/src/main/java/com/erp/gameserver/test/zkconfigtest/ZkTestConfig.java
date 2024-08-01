package com.erp.gameserver.test.zkconfigtest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test")
public class ZkTestConfig {

    private String testStr;

    private int testInt;

    private TestObj testObj;

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public TestObj getTestObj() {
        return testObj;
    }

    public void setTestObj(TestObj testObj) {
        this.testObj = testObj;
    }
}
