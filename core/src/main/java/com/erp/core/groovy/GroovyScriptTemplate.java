package com.erp.core.groovy;

import com.alibaba.fastjson2.JSONObject;
import com.erp.core.logger.Logger;
import com.erp.core.utils.JsonUtil;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class GroovyScriptTemplate {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Set<String> matchServerIds() {
        return null;
    }

    private final LinkedList<String> printStrList = new LinkedList<>();

    protected <T> T getBean(Class<T> aClazz) {
        return applicationContext.getBean(aClazz);
    }


    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public abstract Object invoke();

    /** 输出 */
    protected void print(Object o) {
        String message = String.valueOf(o);
        printStrList.add(message);
        Logger.getLogger(this).warn("groovy shell print: {}", message);
    }

    public List<String> getPrintStrList() {
        return printStrList;
    }

    protected void print(String messageFormat, Object... args) {
        print(MessageFormatter.arrayFormat(messageFormat, args).getMessage());
    }


    protected JsonObject createJson() {
        return new JsonObject();
    }

    public static class JsonObject {
        private final JSONObject map = new JSONObject();

        public JsonObject put(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public Map<String, Object> toMap() {
            return map;
        }

        @Override
        public String toString() {
            return JsonUtil.toStandardJsonString(map);
        }
    }
}
