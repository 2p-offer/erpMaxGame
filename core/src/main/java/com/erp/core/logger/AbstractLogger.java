package com.erp.core.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.Supplier;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractLogger {

    private final org.apache.logging.log4j.Logger logger;

    protected AbstractLogger(String o) {
        logger = LogManager.getLogger(o);
    }

    protected AbstractLogger(Class<?> o) {
        logger = LogManager.getLogger(o);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }


    public void debug(String message, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, paramsFormat(params));
        }
    }

    public void debug(String message, Supplier<?> params) {
        if (logger.isDebugEnabled()) {
            debug(message, params.get());
        }
    }

    public void info(String message, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(message, paramsFormat(params));
        }
    }

    public void info(String message, Supplier<?> params) {
        if (logger.isInfoEnabled()) {
            info(message, params.get());
        }
    }

    public void warn(String message, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(message, paramsFormat(params));
        }
    }

    public void warn(String message, Supplier<?> params) {
        if (logger.isWarnEnabled()) {
            warn(message, params.get());
        }
    }

    public void warn(Throwable throwable) {
        if (logger.isWarnEnabled()) {
            logger.warn("", throwable);
        }
    }

    public void error(String message, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(message, paramsFormat(params));
        }
    }

    public void error(String message, Supplier<?> params) {
        if (logger.isErrorEnabled()) {
            error(message, params.get());
        }
    }

    public void error(Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error("", throwable);
        }
    }

    public void fatal(String message, Object... params) {
        if (logger.isFatalEnabled()) {
            logger.fatal(message, paramsFormat(params));
        }
    }

    public void fatal(String message, Supplier<?> params) {
        if (logger.isFatalEnabled()) {
            fatal(message, params.get());
        }
    }

    public void fatal(Throwable throwable) {
        if (logger.isFatalEnabled()) {
            logger.fatal("", throwable);
        }
    }

    /** 解析日志参数为 JSON 格式 */
    private static Object[] paramsFormat(Object... params) {
        Object[] ans = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if (Objects.isNull(param)) {
                ans[i] = null;
            } else if (isThrowable(param)) {
                ans[i] = param;
            } else {
                ans[i] = String.valueOf(param);
            }
        }
        return ans;
    }

    /** 是否为异常或者错误 */
    private static boolean isThrowable(@Nonnull Object param) {
        return param instanceof Throwable;
    }

}