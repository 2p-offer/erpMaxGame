package com.erp.core.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * 运行时异常基类
 */
public class FormationRuntimeException extends RuntimeException {
    public FormationRuntimeException(String message) {
        super(message);
    }

    public FormationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormationRuntimeException(String messageFormat, Object... args) {
        this(MessageFormatter.arrayFormat(messageFormat, args).getMessage(),
                args.length > 0 && args[args.length - 1] instanceof Throwable throwable ? throwable : null);
    }
}
