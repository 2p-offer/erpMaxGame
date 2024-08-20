package com.erp.gameserver.resolver;

import com.erp.common.exception.LogicException;
import com.erp.common.exception.ResCode;
import com.erp.common.exception.ServerStartFailException;
import com.erp.core.bean.BeanManager;
import com.erp.net.channel.NettyNetChannel;
import com.erp.net.msg.NetMsg;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 消息链式调用处理类
 */
@Component
public class ResolverChainServiceImpl {

    /** 默认的消息处理链 */
    private final List<? extends ResolverChain> defaultResolverChain;

    /** 消息号与消息处理链的映射关系 */
    private final Map<Integer, List<? extends ResolverChain>> codeResolverChain;


    public ResolverChainServiceImpl() throws ServerStartFailException {
        codeResolverChain = new HashMap<>();
        defaultResolverChain = createDefaultResolverChain();
        for (CodeResolverType codeResolverType : CodeResolverType.values()) {
            if (Objects.equals(codeResolverType, CodeResolverType.DEFAULT)) {
                continue;
            }
            int code = codeResolverType.getCode();
            List<? extends ResolverChain> resolverChains = codeResolverType.getChains()
                    .stream()
                    .map(BeanManager::getBean)
                    .collect(Collectors.toList());
            if (codeResolverChain.containsKey(code)) {
                throw new ServerStartFailException(ResCode.ERROR_CODE_RESOLVER_REPLY, "消息的链式调用配置重复:{}", code);
            }
            codeResolverChain.put(code, resolverChains);
        }
        if (Objects.isNull(defaultResolverChain) || defaultResolverChain.isEmpty()) {
            throw new ServerStartFailException(ResCode.ERROR_CODE_RESOLVER_CHAIN_NULL, "默认消息号对应的处理链未配置");
        }
    }

    private List<? extends ResolverChain> createDefaultResolverChain() {
        return CodeResolverType.DEFAULT.getChains().stream()
                .map(BeanManager::getBean)
                .toList();
    }

    public void resolve(NettyNetChannel channel, NetMsg msg) {
        int msgCode = msg.getMsgCode();
        ResolverChainContext context = new ResolverChainContext();
        context.setChannel(channel);
        context.setMsg(msg);
        List<? extends ResolverChain> resolverChains = codeResolverChain.getOrDefault(msgCode, defaultResolverChain);
        Iterator<? extends ResolverChain> iterator = resolverChains.iterator();
        context.setChainQueue(iterator);
        if (!iterator.hasNext()) {
            throw new LogicException(ResCode.ERROR_CODE_RESOLVER_CHAIN_NULL, "消息号对应的处理链配置异常,{}", msgCode);
        }
        while (iterator.hasNext()) {
            iterator.next().resolve0(context);
        }
    }

}
