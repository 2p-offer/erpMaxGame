package com.erp.core.exception;

/** 业务响应码 */
public enum ResCodeEnum {
    /** 成功响应 */
    OK(0, "处理成功", ResCodeLevelTypeEnum.SUCCESS),


    // ===== 999XXX 系统级别错误

    ERROR_CODE_RESOLVER_REPLY(999000, "服务器启动错误,消息号对应的处理链重复配置", ResCodeLevelTypeEnum.ERROR),
    ERROR_CODE_RESOLVER_CHAIN_NULL(999001, "服务器启动错误,默认消息号对应的处理链未配置", ResCodeLevelTypeEnum.ERROR),
    ERROR_UNKNOWN(999999, "未知错误", ResCodeLevelTypeEnum.ERROR);

    private final int code;

    private final String desc;

    private final ResCodeLevelTypeEnum level;

    ResCodeEnum(int code, String desc, ResCodeLevelTypeEnum level) {
        this.code = code;
        this.desc = desc;
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public ResCodeLevelTypeEnum getLevel() {
        return level;
    }
}
