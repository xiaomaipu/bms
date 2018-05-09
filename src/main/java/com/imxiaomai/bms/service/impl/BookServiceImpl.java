package com.imxiaomai.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.*;
import com.imxiaomai.bms.mapper.BookMapper;
import com.imxiaomai.bms.mapper.BookUserRecordMapper;
import com.imxiaomai.bms.mapper.BorrowBookMapper;
import com.imxiaomai.bms.service.BookService;
import com.imxiaomai.bms.util.BookConsts;
import com.imxiaomai.bms.util.CommonConsts;
import com.imxiaomai.bms.util.DateUtil;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author lvsheng
 * @Description:
 * @date 2018/1/23 13:52
 */
@Service
public class BookServiceImpl implements BookService {

    private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookUserRecordMapper bookUserRecordMapper;

    @Resource
    private BorrowBookMapper borrowBookMapper;


    public final Integer SUCCESS = 200;// 成功
    public final Integer DATA_NOT_FOUND = 404;// 数据不存在



    @Override
    public PageInfo<Book> selectPageList(Integer pageNum, Integer pageSize, Book book) {
        logger.info("*******************开始查询图书列表*************************");
        if(book == null){
            book = new Book();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Book> list = bookMapper.queryList(book);
        PageInfo<Book> pageInfo = new PageInfo<Book>(list);
        return pageInfo;
    }

    @Override
    public Book selectDetail(Book book) {
        logger.info("*******************开始查询图书详情*************************");
        Book rebook = null;
        try{
            rebook = bookMapper.selectDetail(book);
            if(rebook !=null){
                logger.info("*******************开始查询此书收藏数量，图书ID："+rebook.getId()+"*************************");
                Integer collNum = bookMapper.queryCollNum(rebook);
                rebook.setCollNum(collNum);
                logger.info("*******************查询当前用户是否收藏过本书，图书ID："+rebook.getId()+"，用户ID："+book.getUserId()+"*************************");
                Integer isColl = bookMapper.queryCollNumByUser(book);
                rebook.setIsColl(isColl);
//                logger.info("*******************开始查询此书读后感，图书ID："+rebook.getId()+"*************************");
//                BookUserRecord br = new BookUserRecord();
//                br.setBookId(rebook.getId());
//                List<BookUserRecord> brm = bookUserRecordMapper.queryList(br);
//                rebook.setBrlist(brm);
                logger.info("*******************开始查询此书已借人数，图书ID："+rebook.getId()+"***********************************");
                BorrowBook bb = new BorrowBook();
                bb.setBookId(rebook.getId());
                List<BorrowBook> rebb = bookUserRecordMapper.queryListBorrow(bb);
                if(rebb !=null){
                    rebook.setRecorded(String.valueOf(rebb.size()));
                    logger.info("*******************获取正在预约的用户，图书ID："+rebook.getId()+"***********************************");
                    for(BorrowBook bbs : rebb){
                        //状态：10已借  20 已还 30 已预约
                        if(bbs.getStatus() == 30){
                            User user = new User();
                            user.setId(bbs.getUserId());
                            user.setMobile(bbs.getMobile());
                            user.setUserName(bbs.getUserName());
                            rebook.setRecordUser(user);
                        }
                    }
                }
            }
        }catch (Exception e ){
            logger.error("*******************查询图书详情失败*************************",e);
        }
        return rebook;
    }

    @Override
    @Transient
    public Book saveOrUpdateInfo(Book book) throws Exception {
        logger.info("添加或修改图书,参数 "+ JSON.toJSONString(book));
        if(!ObjectUtils.isEmpty(book)){
            Integer bookId = book.getId();
            if(ObjectUtils.isEmpty(bookId)){
                //添加图书
                String bookName = book.getBookName();
                if(ObjectUtils.isEmpty(bookName)){
                    throw new RuntimeException("参数不全,书名必须传递");
                }
                Book queryBook = new Book();
                queryBook.setBookName(bookName);
                Book res = bookMapper.selectOne(queryBook);
                if(!ObjectUtils.isEmpty(res)){
                    throw new RuntimeException("书名:"+bookName+" 的书籍已经存在");
                }
                String now = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
                book.setCreated(now);
                book.setStatus(BookConsts.BOOK_STATUS_EFFECT);
                bookMapper.saveBook(book);
            }else{
                //修改图书
                String now = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
                book.setUpdated(now);
                bookMapper.updateByPrimaryKeySelective(book);
            }
        }
        return book;
    }

    @Override
    public Book selectSimpleInfoByExample(Book book) throws Exception {
        logger.info("查询书籍基本信息,参数 "+JSON.toJSONString(book));
        if(!ObjectUtils.isEmpty(book)){
            book.setYn(1);
            Book res = bookMapper.selectOne(book);
            return res;
        }
        return null;
    }

    @Override
    public PageInfo<Book> selectSimplePageInfo(Book book,Integer pageNum,Integer pageSize) throws Exception {
        logger.info("分页查询书籍信息");
        if(book == null){
            book = new Book();
        }
        if(pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        book.setYn(1);
        PageHelper.startPage(pageNum,pageSize);
        List<Book> list = bookMapper.select(book);
        PageInfo<Book> pageInfo = new PageInfo<Book>(list);
        return pageInfo;
    }

    @Override
    public ResMsg<String> userborrow(Book book) throws Exception {
        logger.info("*******************用户开始借书流程*************************");
        ResMsg<String> msg = new ResMsg<String>();

        //1.查询用户是否有未还的书
        BorrowBook borrowb = new BorrowBook();
        borrowb.setUserId(book.getUserId());
        borrowb.setBookId(book.getId());
        borrowb.setStatus(10);//状态：10已借  20 已还 30 已预约
        BorrowBook bb = borrowBookMapper.queryBorrowBookByUser(borrowb);
        if(bb != null){//有未还的书
            msg.setCode(DATA_NOT_FOUND);
            msg.setDes("您有未还的书，请先去还书");
            logger.info("*******************您有未还的书，请先去还书*************************");
            return msg;
          }

        //2.是否已被借走
       Book rebook = bookMapper.selectDetail(book);
        if(rebook.getStatus() == 20){//图书状态：10可借  20 已借 40 已下架
            msg.setCode(DATA_NOT_FOUND);
            msg.setDes("此书已被借走");
            logger.info("*******************此书已被借走*************************");
            return msg;
        }

        //3.是否已被预约
        BorrowBook borrowbook = new BorrowBook();
        borrowbook.setBookId(book.getId());
        borrowbook.setStatus(30);//状态：10已借  20 已还 30 已预约
        BorrowBook bbook = borrowBookMapper.queryBorrowBookByUser(borrowbook);
        if(bbook != null){//此书已被预约
            msg.setCode(DATA_NOT_FOUND);
            msg.setDes("此书已被预约");
            logger.info("*******************此书已被预约*************************");
            return msg;
        }
        //4.可以借书了
        Book ibook =  new Book();
        ibook.setId(book.getId());
        ibook.setStatus(20);//图书状态：10可借  20 已借 40 已下架
        //改变图书的状态
        int returnbook = bookMapper.update(ibook);
        //添加借书记录
        BorrowBook addbb = new BorrowBook();
        addbb.setBookId(book.getId());
        addbb.setUserId(book.getUserId());
        addbb.setStatus(10);//状态：10已借  20 已还 30 已预约
        int borrowi =  borrowBookMapper.save(addbb);
        if(returnbook>0 && borrowi>0){
            msg.setCode(SUCCESS);
            msg.setDes("借书成功");
            logger.info("*******************借书成功*************************");
        }
        return msg;
    }

    @Override
    public ResMsg<String> usermake(Book book) throws Exception {
        logger.info("*******************用户开始预约*************************");
        ResMsg<String> msg = new ResMsg<String>();
        //1.查询此书是否被预约
        BorrowBook borrowbook = new BorrowBook();
        borrowbook.setBookId(book.getId());
        borrowbook.setStatus(30);//状态：10已借  20 已还 30 已预约
        BorrowBook bbook = borrowBookMapper.queryBorrowBookByUser(borrowbook);
        if(bbook != null){//此书已被预约
            msg.setCode(DATA_NOT_FOUND);
            msg.setDes("此书已被预约");
            logger.info("*******************此书已被预约*************************");
            return msg;
        }
        //2.开始预约
        BorrowBook addbb = new BorrowBook();
        addbb.setBookId(book.getId());
        addbb.setUserId(book.getUserId());
        addbb.setStatus(30);//状态：10已借  20 已还 30 已预约
        int borrowi =  borrowBookMapper.save(addbb);
        if(borrowi>0 && borrowi>0){
            msg.setCode(SUCCESS);
            msg.setDes("预约成功");
            logger.info("*******************预约成功*************************");
        }
        return msg;
    }

    @Override
    public ResMsg<String> discuss(BookDiscuss bookDiscuss) throws Exception {
        logger.info("*******************用户开始评论图书，图书ID"+bookDiscuss.getBookId()+"*************************");
        ResMsg<String> msg = new ResMsg<String>();
        bookDiscuss.setYn(1);
        bookDiscuss.setCreator(bookDiscuss.getUserId().toString());
        int add =  bookMapper.saveBd(bookDiscuss);
        if(add>0){
            msg.setCode(SUCCESS);
            msg.setDes("评论成功");
            logger.info("*******************评论成功*************************");
        }else{
            msg.setCode(DATA_NOT_FOUND);
            msg.setDes("评论失败");
            logger.info("*******************评论失败，图示ID："+bookDiscuss.getBookId()+"*************************");
        }
        return msg;
    }

    @Override
    public PageInfo<BookDiscuss> selectDiscussPageInfo(BookDiscuss book, Integer pageNum, Integer pageSize) throws Exception {
        logger.info("*******************开始查询图书评论列表*************************");
        if(book == null){
            book = new BookDiscuss();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<BookDiscuss> list = bookMapper.queryBookDisucssList(book);
        PageInfo<BookDiscuss> pageInfo = new PageInfo<BookDiscuss>(list);
        return pageInfo;
    }
}
