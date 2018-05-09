package com.imxiaomai.bms.enums;

/**
 *
 * @author KuKan
 * @date 2018/1/28
 */
public enum  EntityDelState {
    /**
     * 删除状态
     */
    DELETED(0),
    /**
     * 正常，非删除状态
     */
    NORMAL(1);
    private int code;

    EntityDelState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
