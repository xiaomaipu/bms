package com.imxiaomai.bms.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.*;
import com.imxiaomai.bms.service.BookService;
import com.imxiaomai.bms.service.BookTypeService;
import com.imxiaomai.bms.service.BorrowBookService;
import com.imxiaomai.bms.service.UserService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvsheng
 * @Description: TODO(借书)
 * @date 2018/1/23 13:52
 */
@Controller
@RequestMapping("/borrow")
public class BorrowAdminController extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(BorrowAdminController.class);


    @Resource
    private BorrowBookService borrowBookService;
    @Resource
    private BookTypeService bookTypeService;
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;


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


    @GetMapping("/queryList")
    public  String queryList(
                @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
                Book book,ModelMap modelMap) {
        try {
            PageInfo<BorrowBook> pageBook = borrowBookService.queryListByStatus(pageNum, pageSize,book);
            modelMap.put("borrowPage",pageBook);
            modelMap.put("borrowParam",book);
            return "borrow/list";
        } catch (Exception e) {
            return "error";
        }
    }
    @GetMapping("/toBorrowAddPage/{bookId}")
    public String toBorrowAddPage(@PathVariable(value = "bookId") Integer bookId,ModelMap modelMap){
        try {
            Book queryBook = new Book();
            queryBook.setId(bookId);
            Book book = bookService.selectSimpleInfoByExample(queryBook);
            PageInfo<User> userPageInfo = userService.selectPageList(null,1,1000);
            modelMap.put("book",book);
            modelMap.put("users",userPageInfo.getList());
            return "borrow/add";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @ResponseBody
    @PostMapping("/{id}/{status}")
    public ResMsg updateBorrowStatus(@PathVariable(value = "id") Integer borrowId,@PathVariable Integer status){
        try {
            boolean hasUpdated = borrowBookService.updateBookStatus(borrowId,status);
            if (hasUpdated){
                return success("更新成功");
            }else {
                return fail(SERVER_FAIL,"更新结束记录状态失败",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error("服务器异常",e);
        }
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

    @RequestMapping("userborrow")
    @ResponseBody
    public ResMsg<String> userborrow(Book book){
        logger.info("*********************用户借书,参数 "+ JSON.toJSONString(book));
        ResMsg<String> msg = new ResMsg<String>();
        try {
            msg =bookService.userborrow(book);
        } catch (Exception e) {
            logger.error("用户借书异常",e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("用户借书异常");
        }
        return msg;
    }

    @ModelAttribute
    public void setBookTypes(ModelMap map){
        PageInfo<BookType> bookTypePageInfo = bookTypeService.selectPageList(1,1000,null);
        if (bookTypePageInfo == null || bookTypePageInfo.getList() == null || bookTypePageInfo.getList().isEmpty()){
            return;
        }
        map.put("bookTypes",bookTypePageInfo.getList());
    }
}
