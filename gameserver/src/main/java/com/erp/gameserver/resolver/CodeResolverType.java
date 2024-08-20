package com.erp.gameserver.resolver;

import com.erp.net.constant.NetConstant;

import java.util.List;

/**
 * 定义各种消息的处理链方案
 */
public enum CodeResolverType {

    /** 默认 */
    DEFAULT(-1,
            List.of(
                    AccessLogChain.class,
                    ParseRequestChain.class,
                    BizChain.class,
                    SendResponseChain.class
            )),
    /** 登录 */
    LOGIN(NetConstant.LOGIN_CODE,
            List.of(
                    AccessLogChain.class,
                    ParseRequestChain.class,
                    LoginChain.class,
                    SendResponseChain.class
            ));
    private final int code;

    private final List<Class<? extends ResolverChain>> chains;


    CodeResolverType(int code, List<Class<? extends ResolverChain>> chains) {
        this.code = code;
        this.chains = chains;
    }

    public int getCode() {
        return code;
    }

    public List<Class<? extends ResolverChain>> getChains() {
        return chains;
    }
}
