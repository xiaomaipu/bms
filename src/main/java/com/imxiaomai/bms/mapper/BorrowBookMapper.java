package com.imxiaomai.bms.mapper;

import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BorrowBook;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.List;

/**
 * Created by hyy on 2018/1/24.
 */
public interface BorrowBookMapper extends Mapper<BorrowBook>{

    /**
     * 获取还书列表
     * @param param
     * @return
     */
    List<BorrowBook> queryList(Book param);


    /**
     * 用户还书
     * @param bb
     * @return
     * @throws Exception
     */
    int update(BorrowBook bb) throws Exception;


    /**
     * 查询用户是否有未还的书
     * @param borrowBook
     * @return
     */
    BorrowBook queryBorrowBookByUser(BorrowBook borrowBook) throws ParseException;


    /**
     * 添加借书记录
     * @param bb
     * @return
     * @throws Exception
     */
    int save(BorrowBook bb) throws Exception;

}
