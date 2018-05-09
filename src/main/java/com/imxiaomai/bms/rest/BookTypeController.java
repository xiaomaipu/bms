package com.imxiaomai.bms.rest;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.BookType;
import com.imxiaomai.bms.service.BookTypeService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

/**
 * @author lvsheng
 * @Description: TODO(图书分类)
 * @date 2018/1/27 13:52
 */
@Controller
@RequestMapping("/rest/type")
public class BookTypeController extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(BookTypeController.class);

    @Resource
    private BookTypeService bookTypeService;

    /**
     * 添加
     * @param book
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public  ResMsg<String> add(BookType book) {
        ResMsg<String> msg = new ResMsg<String>();
        if(book.getName() == null || book.getName() == ""){
            msg.setCode(PARAM_ERROR);
            msg.setDes("分类名称不能为空");
            return msg;
        }
        try {
            msg = bookTypeService.save(book);
        } catch (Exception e) {
            logger.error("*************添加图书失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("添加图书失败,请稍后再试");
        }
        return msg;
    }

    /**
     * 获取图书分类列表
     * @param pageNum
     * @param book
     * @return
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public  ResMsg<PageInfo<BookType>> queryList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                 @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
                                                 BookType book) {
        ResMsg<PageInfo<BookType>> msg = new ResMsg<PageInfo<BookType>>();
        try {
            PageInfo<BookType> pageBook = bookTypeService.selectPageList(pageNum, pageSize,book);
            msg.setCode(SUCCESS);
            msg.setDes(OP_SUCCESS);
            msg.setObj(pageBook);
        } catch (Exception e) {
            logger.error("*************查询图书分类列表失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("查询图书分类列表失败,请稍后再试");
        }
        return msg;
    }


    /**
     * 获取图书分类列表
     * @param book
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public  ResMsg<String> update(BookType book) {
        ResMsg<String> msg = new ResMsg<String>();
        //TODO 登录后用户信息
        book.setUpdator("3");
        try {
            int i  = bookTypeService.update(book);
            if(i>0){
                msg.setCode(SUCCESS);
                msg.setDes("更新成功");
            }else{
                msg.setCode(DATA_NOT_FOUND);
                msg.setDes("更新失败");
            }
        } catch (Exception e) {
            logger.error("*************更新图书分类失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("更新图书分类失败,请稍后再试");
        }
        return msg;
    }


    /**
     *
     * @param book
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public  ResMsg<String> del(BookType book) {
        ResMsg<String> msg = new ResMsg<String>();
        //TODO 登录后用户信息
        book.setUpdator("3");
        try {
            msg  = bookTypeService.del(book);
        } catch (Exception e) {
            logger.error("*************删除图书分类失败******************", e);
            msg.setCode(SERVER_ERROR);
            msg.setDes("删除图书分类失败,请稍后再试");
        }
        return msg;
    }

}
