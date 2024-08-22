package com.erp.common.exception;


import com.erp.core.exception.FormationRuntimeException;

public class LogicException extends FormationRuntimeException {
    private final ResCodeEnum resCode;
    private final Object[] resParams;

    public LogicException(ResCodeEnum resCode, String resMsg, Object... params) {
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
