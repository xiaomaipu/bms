package com.imxiaomai.bms.service;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.BorrowBook;
import com.imxiaomai.bms.vo.BorrowBookVo;

import java.text.ParseException;
import java.util.List;

/**
 * Created by hyy on 2018/1/25.
 */
public interface BorrowBookService {

    /**
     * 查询借书信息
     * @param borrowBook
     * @return
     * @throws Exception
     */
    List<BorrowBookVo> selectBorrowListByExample(BorrowBook borrowBook) throws Exception;


    /**
     * 分页查询借书信息
     * @param borrowBook
     * @return
     * @throws Exception
     */
    PageInfo<BorrowBookVo> selectPageListByExample(BorrowBook borrowBook, Integer pageNum, Integer pageSize) throws Exception;


	/**
	 * 获取借书列表
	 * @param book
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<BorrowBook> selectPageList(Integer pageNum, Integer pageSize, Book book) throws ParseException;


	/**
	 * 获取借书详情
	 * @param book
	 * @return
	 */
	Book selectDetail(Book book,String borrowDate) throws ParseException;


	/**
	 * 写读后感
	 * @param bur
	 * @return
	 */
	boolean saveBookUserRecord(BookUserRecord bur);



	/**
	 * 获取还书列表
	 * @param book
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<BorrowBook> queryListByStatus(Integer pageNum, Integer pageSize, Book book) throws ParseException;


	/**
	 * 用户还书
	 * @param bookType
	 * @return
	 * @throws Exception
	 */
	int update(BorrowBook bookType) throws Exception;

	/**
	 * 还书
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	boolean updateBookStatus(Integer borrowId,Integer status) throws Exception;


	/**
	 * 查询用户是否有未还的书
	 * @param borrowBook
	 * @return
	 */
	BorrowBook queryBorrowBookByUser(BorrowBook borrowBook) throws ParseException;
}
