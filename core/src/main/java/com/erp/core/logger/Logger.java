package com.erp.core.logger;

import org.apache.logging.log4j.util.StackLocatorUtil;

import java.util.IdentityHashMap;

/**
 * 日志输出工具
 *
 * @author erp
 */
public class Logger {

    private Logger() {}

    private static final IdentityHashMap<Object, AbstractLogger> loggerCache = new IdentityHashMap<>();

    public static AbstractLogger getLogger(Object value) {
        if (value instanceof String name) {
            return loggerCache.computeIfAbsent(value, k -> new AbstractLogger(name) {
            });
        } else if(value instanceof Class<?> loggerClass) {
            return loggerCache.computeIfAbsent(loggerClass, k -> new AbstractLogger(loggerClass) {
            });
        } else {
            Class<?> loggerClass = value != null ? value.getClass() : StackLocatorUtil.getCallerClass(2);
            return loggerCache.computeIfAbsent(loggerClass, k -> new AbstractLogger(loggerClass) {
            });
        }
    }

}
