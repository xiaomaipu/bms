package com.imxiaomai.bms.mapper;

import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.BorrowBook;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lvsheng on 2018/1/23
 */
public interface BookUserRecordMapper extends Mapper<BookUserRecord>{

    /**
     * 获取读后感列表
     * @param param
     * @return
     */
    List<BookUserRecord> queryList(BookUserRecord param);

    /**
     * 获取图书的借阅次数
     * @param param
     * @return
     */
    List<BorrowBook> queryListBorrow(BorrowBook param);

    /**
     * 获取读后列表,包括用户名、手机号、书名、书图、读后感信息
     * @param bookUserRecord
     * @return
     */
    List<BookUserRecord> selectDetailListByExample(BookUserRecord bookUserRecord);


    /**
     * 获取借书列表
     * @param param
     * @return
     */
    List<BorrowBook> queryListBorrowByUserId(Book param);

    /**
     * 是否写了读后感
     * @param param
     * @return
     */
    BookUserRecord  isWriterecorded(BookUserRecord param);


    /**
     * 是否写了读后感
     * @param param
     * @return
     */
    int  save(BookUserRecord param);




}
