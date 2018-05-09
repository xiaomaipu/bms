package com.imxiaomai.bms.enums;

/**
 * Created by Admin on 2018/1/29.
 */
public enum  BorrowStateEnum {
    BORROWED(10,"已借"),
    GIVEN_BACK(20,"已还"),
    ORDERED(30,"已预约");
    private int code;
    private String des;

    BorrowStateEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public static BorrowStateEnum getByCode(int code){
        for (BorrowStateEnum stateEnum: BorrowStateEnum.values()){
            if (stateEnum.getCode() == code){
                return stateEnum;
            }
        }
        return null;
    }
}
