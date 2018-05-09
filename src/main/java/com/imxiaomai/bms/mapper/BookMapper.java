package com.imxiaomai.bms.mapper;

import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookDiscuss;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lvsheng on 2018/1/23
 */
public interface BookMapper extends Mapper<Book>{

    /**
     * 获取图书列表
     * @param param
     * @return
     */
    List<Book> queryList(Book param);

    /**
     * 获取图书详情
     * @param book
     * @return
     */
    Book selectDetail(Book book);

    /**
     * 添加图书
     * @param book
     * @return
     */
    int saveBook(Book book);

    /**
     * 更新图书
     * @param book
     * @return
     */
    int update(Book book);

    /**
     * 查询图书分类下的书
     * @param book
     * @return
     */
    Book queryListByType(Book book);


    /**
     * 添加图书评论
     * @param bd
     * @return
     * @throws Exception
     */
    int saveBd(BookDiscuss bd) throws Exception;

    /**
     * 获取图书评论列表
     * @param param
     * @return
     */
    List<BookDiscuss> queryBookDisucssList(BookDiscuss param);

    /**
     * 查询本书收藏人数
     * @param book
     * @return
     */
    int queryCollNum(Book book);

    /**
     * 查询本书收藏人数
     * @param book
     * @return
     */
    int queryCollNumByUser(Book book);

}
