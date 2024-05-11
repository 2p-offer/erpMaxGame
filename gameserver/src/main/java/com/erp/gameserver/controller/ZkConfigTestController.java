package com.erp.gameserver.controller;

import com.erp.core.constant.CoreConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZkConfigTestController {


    @Value("${defdata}")
    private String wy;

    @Value("${erp}")
    private String erp;

    @GetMapping("/test/zk")
    public ResponseEntity<String> getZkNodeData() {
        String emg = "123";
        System.out.println("1232131231===" + wy);
        System.out.println("1232131231==22222=" + erp);
        System.out.println("NAME" + CoreConstants.NAME);
        return ResponseEntity.ok(emg);
    }

}
