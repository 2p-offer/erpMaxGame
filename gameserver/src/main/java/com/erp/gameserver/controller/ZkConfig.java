package com.erp.gameserver.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wy")
public class ZkConfig {
    private String emg;

    private String wy;

    public String getEmg() {
        return emg;
    }

    public void setEmg(String emg) {
        this.emg = emg;
    }

    public String getWy() {
        return wy;
    }

    public void setWy(String wy) {
        this.wy = wy;
    }
}
