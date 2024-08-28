package com.erp.core.servernode;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class ServerNodeRegistry implements ApplicationListener<ApplicationStartedEvent> {

    private final ServiceNodeHelper serviceNodeHelper;
    private final ServerNodeSupplier<?> supplier;

    public ServerNodeRegistry(ServiceNodeHelper serviceNodeHelper, ServerNodeSupplier<?> supplier) {
        this.serviceNodeHelper = serviceNodeHelper;
        this.supplier = supplier;
    }

    @Override
    public void onApplicationEvent(@Nonnull ApplicationStartedEvent event) {
        serviceNodeHelper.registerService(supplier.get());
    }
}
