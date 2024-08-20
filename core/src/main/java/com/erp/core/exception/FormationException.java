package com.erp.core.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 异常基类
 */
public class FormationException extends Exception {
    public FormationException(String message) {
        super(message);
    }

    public FormationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormationException(String messageFormat, Object... args) {
        this(MessageFormatter.arrayFormat(messageFormat, args).getMessage(),
                args.length > 0 && args[args.length - 1] instanceof Throwable throwable ? throwable : null);
    }
}
