package com.erp.gameserver.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ZkConfigTestController {



    @Value("${defdata}")
    private String wy;

    @Value("${erp}")
    private String erp;

    @GetMapping("/test/zk")
    public ResponseEntity<String> getZkNodeData() {

        LogManager.getLogger(this).info("get ZK data,wy:{}", wy);
        throw new RuntimeException("123");
//        return ResponseEntity.ok(emg);
    }

    @GetMapping("/forModel")
    public String onError(ModelMap map) {
        LogManager.getLogger(this).info("request forModel");
        return "error";
    }

    @GetMapping("/responseBody")
    @ResponseBody
    public String returnBody() {
        LogManager.getLogger(this).info("request returnBody");
        LogManager.getLogger(this).error(" error request returnBody");
        return "some data for responseBody";
    }

    @GetMapping("/forResponseEntity")
    public ResponseEntity<String> forResponseEntity() {

        LogManager.getLogger(this).info("get ZK data,erp:{}", erp);
        return ResponseEntity.ok(erp);
    }

}
