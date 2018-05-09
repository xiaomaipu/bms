package com.imxiaomai.bms.web;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.BookType;
import com.imxiaomai.bms.entity.User;
import com.imxiaomai.bms.service.UserService;
import com.imxiaomai.bms.service.impl.UserServiceImpl;
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
import java.util.regex.Pattern;

/**
 *
 * @author hyy
 * @date 2018/1/2
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseAction{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Resource
    private UserService userService;

    private static Pattern normalCharsPattern = Pattern.compile("[\\d\\w]*");
    private static Pattern phonePattern = Pattern.compile("(^1[3|4|5|7|8]\\d{9}$)|(^09\\d{8}$)");

    private final String defaultPassword = "123456";

    @GetMapping("list")
    public String userList(User user, ModelMap modelMap){
        logger.info("查询用户列表");
        try {
            PageInfo<User> pageInfo = userService.selectPageList(user,1, CommonConsts.DEFAULT_PAGE_SIZE*100);
            modelMap.put("userPage",pageInfo);
            return "user/list";
        } catch (Exception e) {
            return "error";
        }
    }
    @ResponseBody
    @PostMapping("/add")
    public  ResMsg add(User user, ModelMap modelMap) {
        if (user.getUserName() == null || "".equals(user.getUserName())){
            return fail(PARAM_ERROR,"用户名不能为空",null);
        }
        if (!normalCharsPattern.matcher(user.getUserName()).matches()){
            return fail(PARAM_ERROR,"用户名只能为字母数字组合",null);
        }
        if (user.getMobile() == null || "".equals(user.getMobile())){
            return fail(PARAM_ERROR,"用户手机号不能为空",null);
        }
        if (!phonePattern.matcher(user.getMobile()).matches()){
            return fail(PARAM_ERROR,"请输入合法手机号",null);
        }
        user.setPassword(defaultPassword);
        try {
            User user1 = userService.saveOrUpdate(user);
            return success(user1);
        } catch (Exception e) {
            e.printStackTrace();
            return error("添加用户信息失败",e);
        }
    }
    @GetMapping("toAddPage")
    public String toAddPage(){
        return "user/add";
    }

    @GetMapping("toUpdatePage/{userName}")
    public String toUpdatePage(@PathVariable(value = "userName") String userName ,ModelMap map){
        try {
            User user =  userService.selectByAccount(userName);
            map.put("user",user);
            return "user/update";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("update")
    @ResponseBody
    public ResMsg update(User user){
        if (ObjectUtils.isEmpty(user.getId())){
            return fail(PARAM_ERROR,"用户id为空",null);
        }
        if (user.getUserName() == null || "".equals(user.getUserName())){
            return fail(PARAM_ERROR,"用户名不能为空",null);
        }
        if (!normalCharsPattern.matcher(user.getUserName()).matches()){
            return fail(PARAM_ERROR,"用户名只能为字母数字组合",null);
        }
        if (user.getMobile() == null || "".equals(user.getMobile())){
            return fail(PARAM_ERROR,"用户手机号不能为空",null);
        }
        if (!phonePattern.matcher(user.getMobile()).matches()){
            return fail(PARAM_ERROR,"请输入合法手机号",null);
        }
        try {
            userService.saveOrUpdate(user);
            return success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return error("更新用户信息失败",e);
        }
    }

}
