package com.erp.client.net;

public class ClientContext {

    private int requestId;

    private int maxResponseId;

    private String account;

    private String rid;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getMaxResponseId() {
        return maxResponseId;
    }

    public void setMaxResponseId(int maxResponseId) {
        this.maxResponseId = maxResponseId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
