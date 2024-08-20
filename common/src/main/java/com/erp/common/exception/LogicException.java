package com.erp.common.exception;


import com.erp.core.exception.FormationRuntimeException;

public class LogicException extends FormationRuntimeException {
    private final ResCode resCode;
    private final Object[] resParams;

    public LogicException(ResCode resCode, String resMsg, Object... params) {
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
