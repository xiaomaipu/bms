package com.imxiaomai.bms.mapper;

import com.imxiaomai.bms.entity.BookType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * Created by lvsheng on 2018/1/27
 */
public interface BookTypeMapper extends Mapper<BookType>{

    /**
     * 查询最后一个分类
     * @param
     * @return
     * @throws Exception
     */
    BookType queryBookType() throws Exception;

    /**
     * 添加图书类型
     * @param bookType
     * @return
     * @throws Exception
     */
    int save(BookType bookType) throws Exception;

    /**
     * 查询分类名称
     * @param
     * @return
     * @throws Exception
     */
    BookType queryBookTypeByName(BookType bt) throws Exception;


    /**
     * 获取图书类型列表
     * @param param
     * @return
     */
    List<BookType> queryList(BookType param);

    /**
     * 更新
     * @param bookType
     * @return
     * @throws Exception
     */
    int update(BookType bookType) throws Exception;


}
