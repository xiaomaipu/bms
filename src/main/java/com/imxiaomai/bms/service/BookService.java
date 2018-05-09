package com.imxiaomai.bms.service;


import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookDiscuss;
import com.imxiaomai.bms.util.ResMsg;

public interface BookService {


	/**
	 * 获取图书列表
	 * @param book
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Book> selectPageList( Integer pageNum, Integer pageSize,Book book);

	/**
	 * 获取图书详情
	 * @param book
	 * @return
	 */
	Book selectDetail(Book book);

	/**
	 * 添加或修改图书
	 * @param book
	 * @return
	 * @throws Exception
	 */
	Book saveOrUpdateInfo(Book book) throws Exception;

	/**
	 * 查询书籍基本信息
	 * @param book
	 * @return
	 * @throws Exception
	 */
	Book selectSimpleInfoByExample(Book book) throws Exception;


	/**
	 * 分页查询图书基本信息
	 * @param book
	 * @return
	 * @throws Exception
	 */
	PageInfo<Book> selectSimplePageInfo(Book book,Integer pageNum,Integer pageSize) throws Exception;


	/**
	 * 用户借书
	 * @param book
	 * @return
	 * @throws Exception
	 */
	ResMsg<String> userborrow(Book book) throws Exception;

	/**
	 * 用户预约
	 * @param book
	 * @return
	 * @throws Exception
	 */
	ResMsg<String> usermake(Book book) throws Exception;



	/**
	 * 图书评论
	 * @param bookDiscuss
	 * @return
	 * @throws Exception
	 */
	ResMsg<String> discuss(BookDiscuss bookDiscuss) throws Exception;

	/**
	 * 分页查询图书基本信息
	 * @param book
	 * @return
	 * @throws Exception
	 */
	PageInfo<BookDiscuss> selectDiscussPageInfo(BookDiscuss book,Integer pageNum,Integer pageSize) throws Exception;
}
