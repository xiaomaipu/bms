package com.imxiaomai.bms.enums;

/**
 * Created by Admin on 2018/1/29.
 */
public enum  BookStateEnum {
    NORMAL(10,"可借"),
    BORROWED(20,"已借"),
    ORDERED(30,"已预约"),
    OFF_SHELF(30,"已下架");
    private int code;
    private String des;

    BookStateEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }
}
