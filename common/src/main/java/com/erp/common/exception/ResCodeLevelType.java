package com.erp.common.exception;

/** 业务错误码级别 */
public enum ResCodeLevelType {
    SUCCESS(0),
    NOTIFY(1),
    EXCEPTION(2),
    ERROR(3);

    private final int level;

    ResCodeLevelType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
