package com.imxiaomai.bms.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 图书
 */
@Table(name = "book")
public class Book implements Serializable{
    private static final long serialVersionUID = 6736457239453512995L;

    public static final Integer PAPER_BOOK = 0;
    public static final Integer E_BOOK = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_des")
    private String bookDes;

    private String img;

    private Integer type;

    private Integer kind;

    private Integer status;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_des")
    private String authorDes;

    @Column(name = "ebook_url")
    private String ebookUrl;

    @Column(name = "ebook_pwd")
    private String ebookPwd;

    private String creator;

    private String updator;

    private String created;

    private String updated;

    @Transient
    private String typeName;

    @Transient
    private boolean hasEBook;

    private Integer yn;

    /**
     * 借书天数
     */
    @Column(name = "borrow_num")
    private Integer borrowNum;

    @Transient
    private String userName;

    @Transient
    private Integer userId;

    @Transient
    private Integer pastDay;

    @Transient
    private Integer collNum;//收藏人数

    @Transient
    private Integer isColl;//是否收藏

    public Integer getIsColl() {
        return isColl;
    }

    public void setIsColl(Integer isColl) {
        this.isColl = isColl;
    }

    public Book(Integer id) {
        this.id = id;
    }


    public Book() {
    }

    public Integer getCollNum() {
        return collNum;
    }

    public void setCollNum(Integer collNum) {
        this.collNum = collNum;
    }

    public Integer getPastDay() {
        return pastDay;
    }

    public void setPastDay(Integer pastDay) {
        this.pastDay = pastDay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(Integer borrowNum) {
        this.borrowNum = borrowNum;
    }

    @Transient
    private List<BookUserRecord> brlist;//读后感

    @Transient
    private User recordUser;//正在借书的用户

    @Transient
    private String recorded;//有多少人已经借过


    @Transient
    private String isWriterecorded;//是否写了读后感 0-未写   1-写了

    public String getIsWriterecorded() {
        return isWriterecorded;
    }

    public void setIsWriterecorded(String isWriterecorded) {
        this.isWriterecorded = isWriterecorded;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }



    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
    }

    public User getRecordUser() {
        return recordUser;
    }

    public void setRecordUser(User recordUser) {
        this.recordUser = recordUser;
    }

    public List<BookUserRecord> getBrlist() {
        return brlist;
    }

    public void setBrlist(List<BookUserRecord> brlist) {
        this.brlist = brlist;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDes() {
        return bookDes;
    }

    public void setBookDes(String bookDes) {
        this.bookDes = bookDes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDes() {
        return authorDes;
    }

    public void setAuthorDes(String authorDes) {
        this.authorDes = authorDes;
    }

    public String getEbookUrl() {
        return ebookUrl;
    }

    public void setEbookUrl(String ebookUrl) {
        this.ebookUrl = ebookUrl;
    }

    public String getEbookPwd() {
        return ebookPwd;
    }

    public void setEbookPwd(String ebookPwd) {
        this.ebookPwd = ebookPwd;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public boolean isHasEBook() {
        return !(ebookUrl ==null || "".equals(ebookUrl));
    }

    public void setHasEBook(boolean hasEBook) {
        this.hasEBook = hasEBook;
    }
}