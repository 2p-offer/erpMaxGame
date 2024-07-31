package com.erp.gameserver.lifecycle;

import com.erp.core.lifecycle.LifecycleBean;
import com.erp.core.logger.Logger;
import org.springframework.stereotype.Component;

@Component
public class PrintLifecycleBean implements LifecycleBean {

    @Override
    public void start() {
        Logger.getLogger(this).info("PRINT >> start");
    }

    @Override
    public void stop() {
        Logger.getLogger(this).info("PRINT >> stop");
    }
}
