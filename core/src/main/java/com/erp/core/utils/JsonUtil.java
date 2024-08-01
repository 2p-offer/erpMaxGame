package com.erp.core.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

public class JsonUtil {

    public static String toStandardJsonString(Object dataObj) {
        return JSON.toJSONString(dataObj);
    }

    public static String toStandardPrettyJsonString(Object dataObj) {
        return JSON.toJSONString(dataObj, JSONWriter.Feature.PrettyFormat);
    }

}
