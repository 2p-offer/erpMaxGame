package com.erp.core.lifecycle;

import com.erp.core.lifecycle.enums.LifecycleOrderType;

public interface LifecycleBean {

    default void start() {
    }

    default void stop() {
    }

    default LifecycleOrderType getOrder(){
        return LifecycleOrderType.DEFAULT;
    }

}
