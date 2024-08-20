package com.erp.common.exception;


import com.erp.core.exception.FormationException;

public class ServerStartFailException extends FormationException {
    private final ResCode resCode;
    private final Object[] resParams;

    public ServerStartFailException(ResCode resCode, String resMsg, Object... params) {
        super("[" + resCode + ", " + resCode.getCode() + "]" + resMsg, params);
        this.resCode = resCode;
        this.resParams = params;
    }

    public ResCode getResCode() {
        return resCode;
    }

    public Object[] getResParams() {
        return resParams;
    }

}
