package com.imxiaomai.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.BorrowBook;
import com.imxiaomai.bms.enums.BookStateEnum;
import com.imxiaomai.bms.enums.BorrowStateEnum;
import com.imxiaomai.bms.mapper.BookMapper;
import com.imxiaomai.bms.mapper.BookUserRecordMapper;
import com.imxiaomai.bms.mapper.BorrowBookMapper;
import com.imxiaomai.bms.service.BorrowBookService;
import com.imxiaomai.bms.util.DateUtil;
import com.imxiaomai.bms.vo.BorrowBookVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hyy on 2018/1/25.
 */
@Service
public class BorrowBookServiceImpl implements BorrowBookService {

    private static Logger logger = LoggerFactory.getLogger(BorrowBookServiceImpl.class);

    @Resource
    private BorrowBookMapper borrowBookMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookUserRecordMapper bookUserRecordMapper;


    @Override
    public List<BorrowBook> selectPageList(Integer pageNum, Integer pageSize, Book book) throws ParseException {
        logger.info("*******************开始查借书列表*************************");
        List<BorrowBook> list = bookUserRecordMapper.queryListBorrowByUserId(book);
        if(list !=null){
            for(BorrowBook bb : list){
                Book bkk = new Book();
                bkk.setId(bb.getBookId());
                Book bk = bookMapper.selectDetail(bkk);
                bb.setBook(bk);
                //计算要还书的天数
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date2 = format.parse(bb.getCreated());//借书当天的日期
                Date date=new Date();
                int day = DateUtil.differentDays(date2,date);//（当前日期-借书日期）-可借天数>0  超过的天数
                bb.setPastDay(day);
            }
        }
        return list;
    }

    @Override
    public Book selectDetail(Book book,String borrowDate) throws ParseException {
        logger.info("*******************开始查借书详细*************************");
        Book rebook = bookMapper.selectDetail(book);
        if(rebook !=null){
            logger.info("*******************开始查询此书读后感，图书ID："+rebook.getId()+"*************************");
            BookUserRecord br = new BookUserRecord();
            br.setBookId(rebook.getId());
            //TODO 登录后要的用户ID
            br.setUserId(book.getUserId());
            List<BookUserRecord> brm = bookUserRecordMapper.queryList(br);
            rebook.setBrlist(brm);
            logger.info("*******************判断用户是否写了读后感，图书ID："+rebook.getId()+"*************************");
            BookUserRecord brd = bookUserRecordMapper.isWriterecorded(br);
            if(brd !=null){
                rebook.setIsWriterecorded("1");
            }else{
                rebook.setIsWriterecorded("0");
            }
        }
        //计算要还书的天数
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = format.parse(borrowDate);//借书当天的日期
        Date date=new Date();
        int day = DateUtil.differentDays(date2,date);//（当前日期-借书日期）-可借天数>0  超过的天数
        rebook.setPastDay(day);
        return rebook;
    }

    @Override
    public boolean saveBookUserRecord(BookUserRecord bur) {
        logger.info("*******************用户写读后感*************************");
        bur.setYn(1);
        int brd =  bookUserRecordMapper.save(bur);
        return brd>0;
    }



    @Override
    public List<BorrowBookVo> selectBorrowListByExample(BorrowBook borrowBook) throws Exception {
        logger.info("查询借书列表,参数 "+ JSON.toJSONString(borrowBook));
        if(borrowBook == null){
            borrowBook = new BorrowBook();
        }
        borrowBook.setYn(1);
        PageHelper.orderBy("created desc");
        List<BorrowBook> list = borrowBookMapper.select(borrowBook);
        List<BorrowBookVo> volist = getVoList(list);

        return volist;
    }

    @Override
    public PageInfo<BorrowBookVo> selectPageListByExample(BorrowBook borrowBook, Integer pageNum, Integer pageSize) throws Exception {
        logger.info("分页查询借书列表,参数 "+ JSON.toJSONString(borrowBook));
        if(borrowBook == null){
            borrowBook = new BorrowBook();
        }
        borrowBook.setYn(1);
        PageHelper.orderBy("created desc");
        PageHelper.startPage(pageNum,pageSize);
        List<BorrowBook> list = borrowBookMapper.select(borrowBook);;
        List<BorrowBookVo> volist = getVoList(list);
        PageInfo<BorrowBookVo> pageInfo = new PageInfo<BorrowBookVo>(volist);
        return pageInfo;
    }

    private List<BorrowBookVo> getVoList(List<BorrowBook> list){
        List<BorrowBookVo> volist = new ArrayList<BorrowBookVo>();
        if(!ObjectUtils.isEmpty(list)){
            for (BorrowBook book : list) {
                BorrowBookVo vo = new BorrowBookVo();
                Integer bookId = book.getBookId();
                Book bookInfo = bookMapper.selectByPrimaryKey(bookId);
                if(!ObjectUtils.isEmpty(bookInfo)){
                    vo.setBookDes(bookInfo.getBookDes());
                    vo.setBookId(bookId);
                    vo.setBookName(bookInfo.getBookName());
                    vo.setBorrowStatus(book.getStatus());
                    vo.setUserId(book.getUserId());
                    volist.add(vo);
                }
            }
        }
        return volist;
    }


    @Override
    public PageInfo<BorrowBook> queryListByStatus(Integer pageNum, Integer pageSize, Book book) throws ParseException {
        logger.info("********************************分页查询还书列表********************************");
        PageHelper.startPage(pageNum,pageSize);
        List<BorrowBook> list = borrowBookMapper.queryList(book);
        if(list !=null){
            for(BorrowBook bb : list){
                Book bkk = new Book();
                bkk.setId(bb.getBookId());
                Book bk = bookMapper.selectDetail(bkk);
                bb.setBook(bk);
                //计算要还书的天数
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date2 = format.parse(bb.getCreated());//借书当天的日期
                Date date=new Date();
                int day = DateUtil.differentDays(date2,date);//（当前日期-借书日期）-可借天数>0  超过的天数
                bb.setPastDay(day);
            }
        }
        PageInfo<BorrowBook> pageInfo = new PageInfo<BorrowBook>(list);
        return pageInfo;
    }

    @Override
    public int update(BorrowBook bb) throws Exception {
        logger.info("********************************确认还书********************************");
        int flag = 0;//更新失败
        //更新图书状态
        Book book = new Book();
        book.setId(bb.getBookId());
        book.setStatus(10);//状态：10可借  20 已借  30 已预约 40 已下架
        book.setUpdator(bb.getUpdator());
        int upbook = bookMapper.update(book);
        int upborrow = borrowBookMapper.update(bb);
        if(upbook>0 && upborrow>0){
            flag = 1;//更新成功
        }
        return flag;
    }

    @Override
    public boolean updateBookStatus(Integer borrowId,Integer status) throws Exception {
        int flagNum1 = 0,flagNum2 = 0;
        BorrowBook borrowBook = borrowBookMapper.selectByPrimaryKey(borrowId);
        if (borrowBook != null){
            borrowBook.setStatus(status);
            flagNum1 = borrowBookMapper.updateByPrimaryKey(borrowBook);
            if (flagNum1>0){
                Integer bookState = exBookStatus(status);
                boolean needUpdateBook = bookState != null;
                if (needUpdateBook){
                    Book book = new Book();
                    book.setId(borrowBook.getBookId());
                    book.setStatus(bookState);
                    flagNum2 = bookMapper.updateByPrimaryKeySelective(book);
                }else {
                    flagNum2 = 1;
                }
            }
        }
        return flagNum1 * flagNum2 > 0;
    }



    private Integer exBookStatus(Integer status) {
        BorrowStateEnum borrowStateEnum = BorrowStateEnum.getByCode(status);
        if (borrowStateEnum == null){
            throw new IllegalArgumentException("传入的借书状态status="+status+"有误,请检查");
        }
        switch (borrowStateEnum){
            case GIVEN_BACK:
                return BookStateEnum.NORMAL.getCode();
            case BORROWED:
                return BookStateEnum.BORROWED.getCode();
            default:
                return null;
        }
    }

    @Override
    public BorrowBook queryBorrowBookByUser(BorrowBook borrowBook) throws ParseException {
        return borrowBookMapper.queryBorrowBookByUser(borrowBook);
    }

}
