package com.imxiaomai.bms.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.User;
import com.imxiaomai.bms.service.UserService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.CommonConsts;
import com.imxiaomai.bms.util.ResMsg;
import com.imxiaomai.bms.util.TokenUtil;
import com.imxiaomai.bms.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by hyy on 2018/1/25.
 */
@Controller("restUserController")
@RequestMapping("/rest/user")
public class UserController extends BaseAction{

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户不存在
     */
    final Integer USER_NOT_EXIST = 401;

    /**
     * 用户密码错误
     */
    final Integer USER_PASSWORD_ERROR = 402;

    @Resource
    private UserService userService;

    @RequestMapping("loginInfo")
    @ResponseBody
    public ResMsg<UserVo> loginInfo(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "password")String password) {
        logger.info("查询用户登录信息,账号:"+userName+" 密码:"+password);
        ResMsg<UserVo> resMsg = new ResMsg<UserVo>();
        try {
            User user = userService.selectByAccount(userName);
            if(ObjectUtils.isEmpty(user)){
                resMsg.setCode(USER_NOT_EXIST);
                resMsg.setDes("用户不存在");
            }else{
                if(!user.getPassword().equals(password)){
                    resMsg.setCode(USER_PASSWORD_ERROR);
                    resMsg.setDes("密码错误");
                }else{
                    UserVo userVo = new UserVo();
                    userVo.setUserId(user.getId());
                    userVo.setUserName(user.getUserName());
                    userVo.setMobile(user.getMobile());
                    String token = TokenUtil.getToken();
                    userService.cacheUserInfo(token,user);
                    resMsg.setCode(SUCCESS);
                    resMsg.setObj(userVo);
                }
            }
        } catch (Exception e) {
            logger.info("根据账号查询会员信息异常",e);
            resMsg.setCode(SERVER_ERROR);
            resMsg.setDes(e.getMessage());
        }
        return resMsg;
    }

    /**
     * 查询会员信息
     * @param id
     * @param userName
     * @param mobile
     * @return
     */
    @RequestMapping("userInfo")
    @ResponseBody
    public ResMsg<User> getUserInfo(@RequestParam(value = "id",required = false)Integer id,
                                    @RequestParam(value = "userName",required = false) String userName,
                                    @RequestParam(value = "mobile",required = false)String mobile){
        logger.info("查询会员信息,id:"+id+" userName:"+userName+" mobile:"+mobile);
        ResMsg<User> resMsg = new ResMsg<User>();
        if(ObjectUtils.isEmpty(id) && ObjectUtils.isEmpty(userName) && ObjectUtils.isEmpty(mobile)){
            resMsg.setCode(PARAM_ERROR);
            resMsg.setDes("参数未传递");
        }else{
            User queryUser = new User();
            if(id != null){
                queryUser.setId(id);
            }
            if(!ObjectUtils.isEmpty(userName)){
                queryUser.setUserName(userName);
            }
            if(!ObjectUtils.isEmpty(mobile)){
                queryUser.setMobile(mobile);
            }
            try {
                User user = userService.selectUserByExample(queryUser);
                if(!ObjectUtils.isEmpty(user)){
                    resMsg.setCode(SUCCESS);
                    resMsg.setObj(user);
                }else{
                    resMsg.setCode(DATA_NOT_ENOUGH);
                    resMsg.setDes("会员信息不存在");
                }
            } catch (Exception e) {
                logger.info("查询会员信息异常",e);
                resMsg.setCode(SERVER_ERROR);
                resMsg.setDes(e.getMessage());
            }
        }
        return resMsg;

    }

    /**
     * 查询读后感详细信息
     * @param userId
     * @param bookId
     * @return
     */
    @RequestMapping("recordList")
    @ResponseBody
    public ResMsg<PageInfo<BookUserRecord>> selectRecordList(
            @RequestParam(value = "userId",required = false)Integer userId,
            @RequestParam(value = "bookId",required = false)Integer bookId,
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",required = false)Integer pageSize){
        logger.info("查询读后感信息，参数 userId:"+userId+" bookId:"+bookId +" pageNum:"+pageNum+" pageSize:"+pageSize);
        if(pageSize == null){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        ResMsg<PageInfo<BookUserRecord>> resMsg = new ResMsg<PageInfo<BookUserRecord>>();
        BookUserRecord bookUserRecord = new BookUserRecord();
        if(userId != null){
            bookUserRecord.setUserId(userId);
        }
        if(bookId != null){
            bookUserRecord.setBookId(bookId);
        }

        try {
            PageInfo<BookUserRecord> pageInfo = userService.selectRecordDetailPageByExample(bookUserRecord,pageNum,pageSize);
            if(!ObjectUtils.isEmpty(pageInfo)){
                resMsg.setCode(SUCCESS);
                resMsg.setObj(pageInfo);
            }else{
                resMsg.setCode(DATA_NOT_ENOUGH);
                resMsg.setDes("查询结果不存在");
            }
        } catch (Exception e) {
            logger.info("查询读后感信息异常",e);
            resMsg.setCode(SERVER_ERROR);
            resMsg.setDes(e.getMessage());
        }
        return resMsg;
    }

    /**
     * 查询用户列表
     * @param userName
     * @param mobile
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("userList")
    @ResponseBody
    public ResMsg<PageInfo<User>> selectUserList(
            @RequestParam(value = "userName",required = false)String userName,
            @RequestParam(value = "mobile",required = false)String mobile,
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",required = false)Integer pageSize
            ){
        logger.info("查询用户列表,参数 userName:"+userName+" mobile:"+mobile);
        if(pageSize == null){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        ResMsg<PageInfo<User>> resMsg = new ResMsg<PageInfo<User>>();
        User user = new User();
        if(!ObjectUtils.isEmpty(userName)){
            user.setUserName(userName);
        }
        if(!ObjectUtils.isEmpty(mobile)){
            user.setMobile(mobile);
        }

        try {
            PageInfo<User> pageInfo = userService.selectPageList(user,pageNum,pageSize);
            if(!ObjectUtils.isEmpty(pageInfo)){
                resMsg.setCode(SUCCESS);
                resMsg.setObj(pageInfo);
            }else{
                resMsg.setCode(DATA_NOT_ENOUGH);
                resMsg.setDes("查询结果不存在");
            }
        } catch (Exception e) {
            logger.info("查询用户列表异常",e);
            resMsg.setCode(SERVER_ERROR);
            resMsg.setDes(e.getMessage());
        }
        return resMsg;
    }

    /**
     * 添加或修改用户信息
     * @param user
     * @return
     */
    @RequestMapping("saveInfo")
    @ResponseBody
    public ResMsg<User> saveUserInfo(User user){
        logger.info("添加或修改用户信息,参数 "+ JSON.toJSONString(user));
        ResMsg<User> resMsg = new ResMsg<User>();
        if(ObjectUtils.isEmpty(user)){
            resMsg.setCode(PARAM_ERROR);
            resMsg.setDes("参数未传递");
        }else{
            try {
                user = userService.saveOrUpdate(user);
                resMsg.setCode(SUCCESS);
                resMsg.setObj(user);
            } catch (Exception e) {
                logger.info("添加或修改用户信息异常",e);
                resMsg.setCode(SERVER_ERROR);
                resMsg.setDes(e.getMessage());
            }
        }
        return resMsg;
    }



}
