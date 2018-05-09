package com.imxiaomai.bms.service;


import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.BookType;
import com.imxiaomai.bms.util.ResMsg;

public interface BookTypeService{

	/**
	 * 添加图书类型
	 * @param bookType
	 * @return
	 * @throws Exception
     */
	ResMsg<String> save(BookType bookType) throws Exception;


	/**
	 * 获取图书列表
	 * @param book
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<BookType> selectPageList(Integer pageNum, Integer pageSize, BookType book);

	/**
	 * 更新
	 * @param bookType
	 * @return
	 * @throws Exception
	 */
	int update(BookType bookType) throws Exception;


	/**
	 * 删除
	 * @param bookType
	 * @return
	 * @throws Exception
	 */
	ResMsg<String>  del(BookType bookType) throws Exception;

	/**
	 * 根据id查找，可查出逻辑删除的记录
	 * @param id 分类id
	 * @return BookType
	 * @throws Exception {@link java.sql.SQLException}
	 */
	BookType findById(Integer id) throws Exception;

	/**
	 * 判断分类是否存在，在{@link #findById(Integer)} 的基础上进行包装,
	 * 只有记录未删除时候返回true
	 * @param id 分类id
	 * @return
	 * @throws Exception
	 */
	boolean isExist(Integer id) throws Exception;

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteById(Integer id) throws Exception;
}
