package com.erp.core.groovy;

import com.alibaba.fastjson2.JSONObject;
import com.erp.core.logger.Logger;
import com.erp.core.utils.JsonUtil;
import com.google.common.base.Joiner;
import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class GroovyScriptExecutor {

    @Autowired
    private ApplicationContext applicationContext;


    public JSONObject invoke(String script) {

        Logger.getLogger(this).info("groovy 脚本尝试执行,内容: \n{}", script);
        JSONObject result = new JSONObject();
        result.put("success", false);
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            @SuppressWarnings("unchecked")
            Class<? extends GroovyScriptTemplate> groovyClass = classLoader.parseClass(script);
            if (!GroovyScriptTemplate.class.isAssignableFrom(groovyClass)) {
                result.put("log", "脚本不是 GroovyScriptTemplate 的派生类");
            }
            GroovyScriptTemplate groovyObject = groovyClass.getDeclaredConstructor().newInstance();
            groovyObject.setApplicationContext(applicationContext);
            Object invoke = groovyClass.getMethod("invoke").invoke(groovyObject);
            Logger.getLogger(this).info("groovy 脚本执行完成,结果: \n{}", JsonUtil.toStandardPrettyJsonString(invoke));
            result.put("success", true);
            result.put("result", invoke);
        } catch (Throwable throwable) {
            result.put("error", Joiner.on("\n") + ExceptionUtils.getStackTrace(throwable));
            Logger.getLogger(this).error("groovy 脚本执行错误", throwable);
        }
        return result;
    }

}
