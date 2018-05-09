package com.imxiaomai.bms.vo;

import java.io.Serializable;

/**
 * Created by hyy on 2018/1/25.
 */
public class BorrowBookVo implements Serializable{
    private static final long serialVersionUID = -7032874323998883834L;
    private Integer bookId;

    private Integer userId;

    private String bookName;

    private String img;

    private String bookDes;

    private Integer borrowStatus;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBookDes() {
        return bookDes;
    }

    public void setBookDes(String bookDes) {
        this.bookDes = bookDes;
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }
}
