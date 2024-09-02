package com.erp.core.event;

import com.erp.core.logger.Logger;
import org.springframework.util.ErrorHandler;

import javax.annotation.Nonnull;

public class EventErrorHandler implements ErrorHandler {

    @Override
    public void handleError(@Nonnull Throwable throwable) {
        Logger.getLogger(this).error("Unexpected error occurred while listening to application event.", throwable);
    }
}
