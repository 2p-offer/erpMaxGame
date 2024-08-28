package com.erp.core.exception;


public class ServerStartFailException extends FormationException {
    private final ResCodeEnum resCode;
    private final Object[] resParams;

    public ServerStartFailException(ResCodeEnum resCode, String resMsg, Object... params) {
        super("[" + resCode + ", " + resCode.getCode() + "]" + resMsg, params);
        this.resCode = resCode;
        this.resParams = params;
    }

    public ResCodeEnum getResCode() {
        return resCode;
    }

    public Object[] getResParams() {
        return resParams;
    }

}
