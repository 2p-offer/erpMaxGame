package com.erp.core.utils;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.Message;

import java.util.function.Supplier;

/**
 * 延迟JSON序列化
 */
public class LazyToJsonString extends LazyToString {

    protected LazyToJsonString(Supplier<String> supplier) {
        super(supplier);
    }

    public static LazyToJsonString valueOf(Object obj) {
        return new LazyToJsonString(() -> JSON.toJSONString(obj));
    }

    public static LazyToJsonString valueOf(Object obj, boolean prettyFormat) {
        return new LazyToJsonString(() -> JSON.toJSONString(obj));
    }

    public static LazyToJsonString valueOf(Message msg) {
        return new LazyToJsonString(() -> ProtoUtil.toJson(msg));
    }

    public static LazyToJsonString valueOf(Message msg, boolean prettyFormat) {
        return new LazyToJsonString(() -> ProtoUtil.toJson(msg, prettyFormat));
    }

}
