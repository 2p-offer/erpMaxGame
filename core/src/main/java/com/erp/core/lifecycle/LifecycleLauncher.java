package com.erp.core.lifecycle;

import com.erp.core.logger.Logger;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@ConditionalOnBean(LifecycleBean.class)
@Component("lifecycleLauncher")
public class LifecycleLauncher implements ApplicationListener<ApplicationStartedEvent> {

    private List<LifecycleBean> lifecycleBeans = new ArrayList<>();

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Map<String, LifecycleBean> beans = applicationContext.getBeansOfType(LifecycleBean.class);
        try {
            Logger.getLogger(this).info("[LIFECYCLE START]begin to start lifecycles");
            beans.values()
                    .stream()
                    .sorted(Comparator.comparingInt(bean -> bean.getOrder().ordinal()))
                    .forEach(bean -> {
                        bean.start();
                        lifecycleBeans.add(bean);
                    });
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

    @PreDestroy
    public void stopAllLifecycleBeans() {
        Logger.getLogger(this).info("[LIFECYCLE STOP]begin to stop lifecycles");
        for (int i = lifecycleBeans.size() - 1; i >= 0; i--) {
            LifecycleBean lifecycleBean = lifecycleBeans.get(i);
            try {
                lifecycleBean.stop();
                Logger.getLogger(this).info("[LIFECYCLE STOP]LifeCycleBean {} stopped", lifecycleBean.getClass().getSimpleName());
            } catch (Exception e) {
                Logger.getLogger(this).error("[LIFECYCLE STOP]stop lifecycle {} error",
                        lifecycleBean.getClass().getSimpleName(), e);
            }
        }
        Logger.getLogger(this).info("[LIFECYCLE STOP]end to stop lifecycles");
    }
}
