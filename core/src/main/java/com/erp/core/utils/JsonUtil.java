package com.erp.core.utils;


import com.alibaba.fastjson2.JSON;

public class JsonUtil {

    public static String toStandardJsonString(Object dataObj) {
        return JSON.toJSONString(dataObj);
    }

}
