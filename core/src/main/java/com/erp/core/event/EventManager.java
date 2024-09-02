package com.erp.core.event;


import com.erp.core.logger.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;

public class EventManager implements ApplicationEventPublisher {
    private static EventManager instance;
    private final ApplicationEventPublisher eventPublisher;

    EventManager(ApplicationContext applicationContext) {
        eventPublisher = applicationContext;
        EventManager.instance = this;
    }

    public static EventManager getInstance() {
        return instance;
    }

    @Override
    public void publishEvent(@NonNull ApplicationEvent event) {
        if (eventPublisher == null) {
            Logger.getLogger(this).error("EVENT_PUBLISHER is null, not started or closed?");
            return;
        }
        eventPublisher.publishEvent(event);
    }

    @Override
    public void publishEvent(@NonNull Object event) {
        throw new IllegalArgumentException("不支持发布 object 参数的事件");
    }

}
