package com.erp.core.groovy;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RestControllerEndpoint(id = "groovy")
public class GroovyEndpoint {

    @Autowired
    private GroovyScriptExecutor scriptExecutor;


    @PostMapping(value = "/groovy_invoke")
    public String invoke(@RequestBody String script) {
        JSONObject invoke = scriptExecutor.invoke(script);
        return invoke.toJSONString(JSONWriter.Feature.LargeObject);
    }
}
