package com.imxiaomai.bms.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookDiscuss;
import com.imxiaomai.bms.service.BookService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.CommonConsts;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lvsheng
 * @Description: TODO(图书)
 * @date 2018/1/23 13:52
 */
@Controller
@RequestMapping("/rest/book")
public class BookController extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Resource
    private BookService bookService;

    /**
     * 获取图书列表
     * @param pageNum
     * @param book
     * @return
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public  ResMsg<PageInfo<Book>> queryList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                         @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
                                         Book book) {
        ResMsg<PageInfo<Book>> msg = new ResMsg<PageInfo<Book>>();
        try {
            PageInfo<Book> pageBook = bookService.selectPageList(pageNum, pageSize,book);
            msg.setCode(SUCCESS);
            msg.setDes(OP_SUCCESS);
            msg.setObj(pageBook);
        } catch (Exception e) {
            logger.error("*************查询图书列表失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("查询图书列表失败,请稍后再试");
        }
        return msg;
    }

    /**
     *  查询图书详情
     * @param book
     * @param book
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public  ResMsg<Book> detail(Book book) {
        ResMsg<Book> msg = new ResMsg<Book>();
        try {
           Book bookdetail = bookService.selectDetail(book);
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
     * 添加或修改图书
     * @param book
     * @return
     */
    @RequestMapping("saveInfo")
    @ResponseBody
    public ResMsg<Book> saveInfo(Book book){
        logger.info("添加或修改图书,参数 "+ JSON.toJSONString(book));
        ResMsg<Book> msg = new ResMsg<Book>();
        try {
            book =bookService.saveOrUpdateInfo(book);
            msg.setCode(SUCCESS);
            msg.setObj(book);
        } catch (Exception e) {
            logger.error("添加或修改书籍信息异常",e);
            msg.setCode(SERVER_ERROR);
            msg.setDes(e.getMessage());
        }
        return msg;
    }

    /**
     * 查询书籍基本信息
     * @param book
     * @return
     */
    @RequestMapping("/simpleInfo")
    @ResponseBody
    public ResMsg<Book> selectSimpleInfo(Book book) {
        logger.info("查询书籍基本信息");
        ResMsg<Book> msg = new ResMsg<Book>();
        try {
            Book res = bookService.selectSimpleInfoByExample(book);
            if(!ObjectUtils.isEmpty(res)){
                msg.setCode(SUCCESS);
                msg.setObj(res);
            }else{
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes("查询结果不存");
            }
        } catch (Exception e) {
            logger.error("查询图书异常", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes(e.getMessage());
        }
        return msg;
    }

    /**
     * 分页查询书籍基本信息
     * @param book
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("simplePageInfo")
    @ResponseBody
    public ResMsg<PageInfo<Book>> selectSimplePageInfo(Book book,
                                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                       @RequestParam(value = "pageSize",required = false)Integer pageSize){
        logger.info("分页查询书籍信息");
        if(pageSize == null){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        ResMsg<PageInfo<Book>> resMsg = new ResMsg<PageInfo<Book>>();
        try {
            PageInfo<Book> pageInfo = bookService.selectSimplePageInfo(book,pageNum,pageSize);
            if(ObjectUtils.isEmpty(pageInfo)){
                resMsg.setCode(DATA_NOT_FOUND);
                resMsg.setDes("查询结果不存");
            }else{
                resMsg.setCode(SUCCESS);
                resMsg.setObj(pageInfo);
            }
        } catch (Exception e) {
            logger.error("分页查询图书异常", e);
            resMsg.setCode(SERVER_ERROR);
            resMsg.setDes(e.getMessage());
        }
        return resMsg;
    }


    /**
     * 用户借书
     * @param book
     * @return
     */
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

    /**
     * 用户预约
     * @param book
     * @return
     */
    @RequestMapping("usermake")
    @ResponseBody
    public ResMsg<String> usermake(Book book){
        logger.info("*********************用户预约,参数 "+ JSON.toJSONString(book));
        ResMsg<String> msg = new ResMsg<String>();
        try {
            msg =bookService.usermake(book);
        } catch (Exception e) {
            logger.error("用户借书异常",e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("用户借书异常");
        }
        return msg;
    }

    //-----------------------------------图书评论-----------------------------------------
    /**
     * 评论图书
     * @param bookDiscuss
     * @return
     */
    @RequestMapping("/discuss")
    @ResponseBody
    public ResMsg<String> discuss(BookDiscuss bookDiscuss){
        ResMsg<String> resMsg = new ResMsg<String>();
        try {
            resMsg = bookService.discuss(bookDiscuss);
        } catch (Exception e) {
            logger.error("评论失败", e);
            resMsg.setCode(SERVER_ERROR);
            resMsg.setDes("评论失败");
        }
        return resMsg;
    }

    /**
     * 查询图书评论列表
     * @param bookDiscuss
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectDiscussPageInfo")
    @ResponseBody
    public ResMsg<PageInfo<BookDiscuss>> selectDiscussPageInfo(BookDiscuss bookDiscuss,
                                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                       @RequestParam(value = "pageSize",required = false)Integer pageSize){
        logger.info("分页查询书籍评论信息");
        if(pageSize == null){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        ResMsg<PageInfo<BookDiscuss>> resMsg = new ResMsg<PageInfo<BookDiscuss>>();
        try {
            PageInfo<BookDiscuss> pageInfo = bookService.selectDiscussPageInfo(bookDiscuss,pageNum,pageSize);
            if(ObjectUtils.isEmpty(pageInfo)){
                resMsg.setCode(DATA_NOT_FOUND);
                resMsg.setDes("查询结果不存");
            }else{
                resMsg.setCode(SUCCESS);
                resMsg.setObj(pageInfo);
            }
        } catch (Exception e) {
            logger.error("分页查询图书评论异常", e);
            resMsg.setCode(SERVER_ERROR);
            resMsg.setDes("查询失败");
        }
        return resMsg;
    }

}
