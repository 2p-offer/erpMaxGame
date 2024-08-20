package com.erp.gameserver.resolver;

import com.erp.gameserver.model.Player;

public interface ResolverChain {

    /**
     * 链式调用接口
     *
     * @param context 消息调用链处理上下文
     */
    void resolve0(ResolverChainContext context);
}
