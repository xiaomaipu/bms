package com.imxiaomai.bms.rest;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.BorrowBook;
import com.imxiaomai.bms.service.BorrowBookService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvsheng
 * @Description: TODO(借书)
 * @date 2018/1/23 13:52
 */
@Controller
@RequestMapping("/rest/borrow")
public class BorrowBookController extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(BorrowBookController.class);


    @Resource
    private BorrowBookService borrowBookService;
    /**
     * 获取借书列表
     * @param pageNum
     * @param book
     * @return
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public  ResMsg<List<BorrowBook>> queryList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                               @RequestParam(value="pageSize",defaultValue="10")Integer pageSize, Book book) {
        ResMsg<List<BorrowBook>> msg = new ResMsg<List<BorrowBook>>();
        try {
            book.setUserId(book.getUserId());
            List<BorrowBook> pageBook = borrowBookService.selectPageList(pageNum, pageSize,book);
            msg.setCode(SUCCESS);
            msg.setDes(OP_SUCCESS);
            msg.setObj(pageBook);
        } catch (Exception e) {
            logger.error("*************查询借书列表失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("查询借书列表失败,请稍后再试");
        }
        return msg;
    }


    /**
     *  查询借书详情
     * @param book
     * @param borrowDate  借书日期
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public  ResMsg<Book> detail(Book book,String borrowDate) {
        ResMsg<Book> msg = new ResMsg<Book>();
        //TODO 登录后要的用户ID
        book.setUserId(book.getUserId());
        try {
            Book bookdetail = borrowBookService.selectDetail(book, borrowDate);
            msg.setCode(SUCCESS);
            msg.setDes(OP_SUCCESS);
            msg.setObj(bookdetail);
        } catch (Exception e) {
            logger.error("*************查询图书详情失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("查询图书详情失败,请稍后再试");
        }
        return msg;
    }


    /**
     *  写读后感
     * @param bur
     * @return
     */
    @RequestMapping("/record")
    @ResponseBody
    public  ResMsg<BookUserRecord> record(BookUserRecord bur) {
        ResMsg<BookUserRecord> msg = new ResMsg<BookUserRecord>();
        //TODO 登录后要的用户ID
        bur.setUserId(bur.getUserId());
        bur.setCreator(String.valueOf(bur.getUserId()));
        try {
            boolean bookdetail = borrowBookService.saveBookUserRecord(bur);
            if(bookdetail){
                msg.setCode(SUCCESS);
                msg.setDes(SAVE_SUCCESS);
            }else{
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes(SAVE_FAIL);
            }
        } catch (Exception e) {
            logger.error("*************写读后感失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("写读后感失败,请稍后再试");
        }
        return msg;
    }

//--------------------------------------还书记录-------------------------------------------------------------------------

    /**
     * 获取还书列表
     * @param pageNum
     * @param
     * @return
     */
    @RequestMapping("/queryListByStatus")
    @ResponseBody
    public  ResMsg<PageInfo<BorrowBook>> queryListByStatus(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                         @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,Book book) {
        ResMsg<PageInfo<BorrowBook>> msg = new ResMsg<PageInfo<BorrowBook>>();
        try {
            //TODO 用户登录
            book.setUserId(book.getUserId());
            PageInfo<BorrowBook> pageBook = borrowBookService.queryListByStatus(pageNum, pageSize,book);
            msg.setCode(SUCCESS);
            msg.setDes(OP_SUCCESS);
            msg.setObj(pageBook);
        } catch (Exception e) {
            logger.error("*************查询还书列表失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("查询还书列表失败,请稍后再试");
        }
        return msg;
    }


    /**
     * 还书
     * @param book
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public  ResMsg<String> update(BorrowBook book) {
        ResMsg<String> msg = new ResMsg<String>();
        //TODO 登录后用户信息
        book.setUpdator("3");
        try {
            int i  = borrowBookService.update(book);
            if(i>0){
                msg.setCode(SUCCESS);
                msg.setDes("还书成功");
            }else{
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes("还书失败");
            }
        } catch (Exception e) {
            logger.error("*************还书失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("还书失败,请稍后再试");
        }
        return msg;
    }

}
