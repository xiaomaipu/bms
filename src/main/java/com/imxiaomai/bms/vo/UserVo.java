package com.imxiaomai.bms.vo;

import java.io.Serializable;

/**
 * Created by hyy on 2018/1/25.
 */
public class UserVo implements Serializable{

    private static final long serialVersionUID = -366743387104683468L;
    private Integer userId;

    private String userName;

    private String mobile;

    private String token;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
