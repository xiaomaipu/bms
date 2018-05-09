package com.imxiaomai.bms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hyy on 2018/1/24.
 */
@Table(name = "users_detail")
public class UsersDetail implements Serializable{

    private static final long serialVersionUID = 6906378307511467063L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "max_borrow_num")
    private Integer maxBorrowNum;

    @Column(name = "max_keep_time")
    private Integer maxKeepTime;

    private Date created;

    private Date updated;

    private Integer yn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMaxBorrowNum() {
        return maxBorrowNum;
    }

    public void setMaxBorrowNum(Integer maxBorrowNum) {
        this.maxBorrowNum = maxBorrowNum;
    }

    public Integer getMaxKeepTime() {
        return maxKeepTime;
    }

    public void setMaxKeepTime(Integer maxKeepTime) {
        this.maxKeepTime = maxKeepTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
