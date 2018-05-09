package com.imxiaomai.bms.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.BookType;
import com.imxiaomai.bms.service.BookService;
import com.imxiaomai.bms.service.BookTypeService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.CommonConsts;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lvsheng
 * @Description: TODO(图书)
 * @date 2018/1/23 13:52
 */
@Controller
@RequestMapping("/book")
public class BookAdminController extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(BookAdminController.class);

    @Resource
    private BookService bookService;
    @Resource
    private BookTypeService bookTypeService;

    /**
     * 获取图书列表
     * @param pageNum
     * @param book
     * @return
     */
    @GetMapping("/queryList")
    public  String queryList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value="pageSize",defaultValue="1000")Integer pageSize,
                             Book book, ModelMap map) {
        try {
            PageInfo<Book> pageBook = bookService.selectPageList(pageNum, pageSize,book);
            map.put("bookPage",pageBook);
            map.put("bookParam",book);
            return "book/list";
        } catch (Exception e) {
            return "error";
        }
    }
    @GetMapping("toAddPage")
    public String toAddPage(ModelMap map){
        return "book/add";
    }

    /**
     * 添加或修改图书
     * @param book
     * @return
     */
    @RequestMapping("/save")
    public String save(Book book,ModelMap map){
        try {
            Book result = bookService.saveOrUpdateInfo(book);
            return "redirect:queryList";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @GetMapping("/toUpdate/{id}")
    public String selectSimpleInfo(@PathVariable(value = "id") Integer id,ModelMap map) {
        try {
            Book conditionBean = new Book();
            conditionBean.setId(id);
            Book res = bookService.selectSimpleInfoByExample(conditionBean);
            if (res == null){
                return "error";
            }else {
                map.put("bookInfo",res);
                return "book/update";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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
