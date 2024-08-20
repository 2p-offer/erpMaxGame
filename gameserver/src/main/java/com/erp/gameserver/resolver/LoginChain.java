package com.erp.gameserver.resolver;

import com.erp.biz.logic.msg.request.SimpleStringMsg;
import com.erp.gameserver.model.Player;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class LoginChain implements ResolverChain {

    @Override
    public void resolve0(ResolverChainContext context) {

        Player player = new Player();
        if (context.getRequest() instanceof SimpleStringMsg.SimpleStringRequest request) {
            player.setId(request.getData());
            context.setPlayer(player);
            context.getChannel().onLoginSuccess(player.getId());
            SimpleStringMsg.SimpleStringResponse response = SimpleStringMsg.SimpleStringResponse.newBuilder()
                    .setData("登录成功,账号:" + player.getId())
                    .build();
            context.setResponse(response);
        }
    }
}
