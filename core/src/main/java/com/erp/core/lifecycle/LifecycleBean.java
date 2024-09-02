package com.erp.core.lifecycle;

import com.erp.core.lifecycle.enums.LifecycleOrderTypeEnum;

public interface LifecycleBean {

    void start();

    default void stop() {
    }

    default LifecycleOrderTypeEnum getOrder(){
        return LifecycleOrderTypeEnum.DEFAULT;
    }

}
