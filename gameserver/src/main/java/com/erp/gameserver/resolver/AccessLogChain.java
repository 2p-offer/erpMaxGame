package com.erp.gameserver.resolver;

import com.erp.core.logger.Logger;
import org.springframework.stereotype.Component;

/** 日志记录链 */
@Component
public class AccessLogChain implements ResolverChain {

    @Override
    public void resolve0(ResolverChainContext context) {
        Logger.getLogger(this).info("收到客户端请求:{}", context.getMsg());
    }
}
