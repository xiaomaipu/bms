package com.imxiaomai.bms.web;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.common.ServerFailException;
import com.imxiaomai.bms.entity.BookType;
import com.imxiaomai.bms.service.BookTypeService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lvsheng
 * @Description: TODO(图书分类)
 * @date 2018/1/27 13:52
 */
@Controller
@RequestMapping("/category")
public class CategoryAdminController extends BaseAction {
    private Logger logger = LoggerFactory.getLogger(CategoryAdminController.class);

    @Resource
    private BookTypeService bookTypeService;

    @GetMapping("")
    public String toIndex(){
        return "redirect:category/queryList?pageNum=1&pageSize=1000";
    }
    @GetMapping("toAdd")
    public String toAddPage(){
        return "category/add";
    }
    /**
     * 添加
     * @param book
     * @return
     */
    @PostMapping("/add")
    public  String add(BookType book,ModelMap modelMap) {
        ResMsg<String> msg = new ResMsg<String>();
        try {
        if(book.getName() == null || book.getName() == ""){
           modelMap.put("failMsg","分类名称不能为空");
           return "/category/toAdd";
        }
            bookTypeService.save(book);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/category";
    }

    /**
     * 获取图书分类列表
     * @param pageNum
     * @param book
     * @return
     */
    @RequestMapping("/queryList")
    public  String queryList(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                 @RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
                                                 BookType book, ModelMap modelMap) {
        ResMsg<PageInfo<BookType>> msg = new ResMsg<PageInfo<BookType>>();
        try {
            PageInfo<BookType> pageBook = bookTypeService.selectPageList(pageNum, pageSize,book);
            modelMap.put("bookTypePage",pageBook);
            return "category/list";
        } catch (Exception e) {
            return "error";
        }
    }


    /**
     * 获取图书分类列表
     * @param book
     * @return
     */
    @PutMapping("/update")
    public String update(BookType book,ModelMap map) {
        ResMsg<String> msg = new ResMsg<String>();
        //TODO 登录后用户信息
        book.setUpdator("3");
        try {
            boolean updated  = bookTypeService.update(book) > 0;
            if (updated){
                return "redirect:/category";
            }else {
                map.put("failMsg","更新图书分类信息失败");
                return "category/update";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 根据主键删除图书分类
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResMsg delete(@PathVariable(value = "id") Integer id){
        try {
            boolean hasDeleted = bookTypeService.deleteById(id);
            if (hasDeleted){
                return success("删除图书分类成功");
            }else {
                return fail(SERVER_FAIL,"删除图书分类失败",null);
            }
        } catch (ServerFailException e) {
            return fail(SERVER_FAIL,e.getMessage(),null);
        }catch (Exception e){
            return error("服务器异常",e);
        }
    }


    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable(value = "id") Integer id,ModelMap map){
        try {
            BookType bookType = bookTypeService.findById(id);
            map.put("bookType",bookType);
            return "category/update";
        } catch (Exception e) {
            logger.error("跳转至更新页面异常 id={}",id,e.getMessage());
            return "error";
        }
    }
}
