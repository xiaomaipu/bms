package com.imxiaomai.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.common.ServerFailException;
import com.imxiaomai.bms.entity.*;
import com.imxiaomai.bms.enums.EntityDelStateEnum;
import com.imxiaomai.bms.mapper.BookMapper;
import com.imxiaomai.bms.mapper.BookTypeMapper;
import com.imxiaomai.bms.service.BookTypeService;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvsheng
 * @Description:
 * @date 2018/1/27 13:52
 */
@Service
public class BookTypeServiceImpl implements BookTypeService {

    private static Logger logger = LoggerFactory.getLogger(BookTypeServiceImpl.class);

    @Resource
    private BookTypeMapper bookTypeMapper;
    @Resource
    private BookMapper bookMapper;


    public final Integer SUCCESS = 200;// 成功
    public final Integer DATA_NOT_FOUND = 404;// 数据不存在


    @Override
    public ResMsg<String> save(BookType bookType) throws Exception {
        logger.info("*******************开始添加图书分类*************************");
        ResMsg<String> msg = new ResMsg<String>();
        try{
            //校验图示分类名称是否已存在
            BookType bookt = bookTypeMapper.queryBookTypeByName(bookType);
            if(bookt != null){
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes(bookType.getName()+"的分类名称已存在！");
                return msg;
            }
            //添加图书之前，获取最后一个图书类型 然后+10 就是新类型的 type
            BookType bt = bookTypeMapper.queryBookType();
            if(bt == null){
                bookType.setType(10);
            }else {
                bookType.setType(bt.getType()+10);
            }
            int ii = bookTypeMapper.save(bookType);
            if(ii>0){
                msg.setCode(SUCCESS);
                msg.setDes("添加成功");
            }else{
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes("添加失败");
            }
        }catch (Exception e){
            logger.error("*******************添加图示失败*************************",e);
        }
        return msg;
    }

    @Override
    public PageInfo<BookType> selectPageList(Integer pageNum, Integer pageSize, BookType book) {
        logger.info("*******************开始查询图书分类列表*************************");
        if(book == null){
            book = new BookType();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<BookType> list = bookTypeMapper.queryList(book);
        PageInfo<BookType> pageInfo = new PageInfo<BookType>(list);
        return pageInfo;
    }

    @Override
    public int update(BookType bookType) throws Exception {
        return bookTypeMapper.update(bookType);
    }

    @Override
    public ResMsg<String>  del(BookType bookType) throws Exception {
        logger.info("*******************删除分类*************************");
        ResMsg<String> msg = new ResMsg<String>();
        try{
            //删除分类之前查询次分类下是否有书，如果有书则不能删除
            Book bk = new Book();
            bk.setType(bookType.getType());
            Book bookBy  = bookMapper.queryListByType(bk);
            if(bookBy != null){
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes("此分类下已有图书");
                return msg;
            }
            //开始更新
            bookType.setYn(0);
          int  upbookType =  bookTypeMapper.update(bookType);
            if(upbookType>0){
                msg.setCode(SUCCESS);
                msg.setDes("删除成功");
            }else{
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes("删除失败");
            }
        }catch (Exception e){
            logger.error("*******************删除分类失败*************************",e);
        }
        return msg;
    }

    @Override
    public BookType findById(Integer id) throws Exception {
        return bookTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean isExist(Integer id) throws Exception {
        BookType bookType = findById(id);
        return bookType != null && EntityDelStateEnum.NORMAL.getCode() == bookType.getYn();
    }

    @Override
    public boolean deleteById(Integer id) throws Exception {
        BookType bookType = findById(id);
        if (bookType!=null && EntityDelStateEnum.NORMAL.getCode() == bookType.getYn()){
            BookType toDelBean = new BookType();
            toDelBean.setId(id);
            toDelBean.setYn(EntityDelStateEnum.DELETED.getCode());
            Book queryBook = new Book();
            queryBook.setType(bookType.getType());
            ResMsg msg = del(bookType);
            if (SUCCESS.equals(msg.getCode())){
                return true;
            }else {
                throw new ServerFailException(msg.getDes());
            }
        }else {
            throw new ServerFailException("要删除的图书分类不存在");
        }
    }

}
