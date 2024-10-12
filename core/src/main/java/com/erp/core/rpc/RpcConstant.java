package com.erp.core.rpc;

import io.grpc.Metadata;

public class RpcConstant {

    // 定义自定义业务错误码的 key
    public static final Metadata.Key<String> CUSTOM_ERROR_CODE_KEY =
            Metadata.Key.of("custom-error-code", Metadata.ASCII_STRING_MARSHALLER);

}
