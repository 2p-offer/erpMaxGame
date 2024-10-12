package com.erp.gameserver.test.json_net;

import com.alibaba.fastjson2.JSONObject;

public class BizRequest {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static void main(String[] args) {

        BizRequest bizRequest = new BizRequest();
        BagMsg.BagUseItemRequest msg = new BagMsg.BagUseItemRequest();
        BagMsg.ReqItem item = new BagMsg.ReqItem();
        item.setTag("abc");
        item.setNum(1990);
        msg.setItem(item);
        String jsonString = JSONObject.toJSONString(msg);
        System.out.println("bizJson:" + jsonString);
        bizRequest.setData(jsonString);
        System.out.println(JSONObject.toJSONString(bizRequest));
    }
}
