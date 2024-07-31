package com.erp.core.lifecycle;

import com.erp.core.logger.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;

@ConditionalOnBean(LifecycleBean.class)
@Component("lifecycleLauncher")
class LifecycleLauncher implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Map<String, LifecycleBean> beans = applicationContext.getBeansOfType(LifecycleBean.class);
        try {
            Logger.getLogger(this).info("[LIFECYCLE START]begin to start lifecycles");
            beans.values()
                    .stream()
                    .sorted(Comparator.comparingInt(bean -> bean.getOrder().ordinal()))
                    .forEach(LifecycleBean::start);
            Logger.getLogger(this).info("[LIFECYCLE START]end to start lifecycles");
        } catch (Throwable e) {
            Logger.getLogger(this).error("[LIFECYCLE START]start lifecycle failure", e);
            SpringApplication.exit(applicationContext, () -> 1);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
