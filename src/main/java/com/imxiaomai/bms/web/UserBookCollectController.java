package com.imxiaomai.bms.web;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.Book;
import com.imxiaomai.bms.entity.User;
import com.imxiaomai.bms.entity.UsersBookCollect;
import com.imxiaomai.bms.service.BookService;
import com.imxiaomai.bms.service.UserService;
import com.imxiaomai.bms.service.UsersBookCollectService;
import com.imxiaomai.bms.util.CommonConsts;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户搜藏
 * Created by hyy on 2018/3/27.
 */
@Controller
@RequestMapping("/rest/user/collect")
public class UserBookCollectController {

    Logger logger = LoggerFactory.getLogger(UserBookCollectController.class);

    @Resource
    private UsersBookCollectService usersBookCollectService;

    @Resource
    private UserService userService;

    @Resource
    private BookService bookService;

    /**
     * 添加用户搜藏 type 1表示搜藏 0表示取消搜藏
     * @return
     */
    @PostMapping("saveInfo")
    @ResponseBody
    public ResMsg<String> saveUserCollect(Integer userId,Integer bookId,Integer type){
        logger.info("添加或修改用户搜藏,userId:"+userId+" bookId:"+bookId+" type:"+type);
        ResMsg<String> resMsg = new ResMsg<String>();

        UsersBookCollect usersBookCollect = new UsersBookCollect();
        usersBookCollect.setUserId(userId);
        usersBookCollect.setBookId(bookId);
        usersBookCollect.setType(type);

        try {
            resMsg = usersBookCollectService.saveUserBookCollect(usersBookCollect);
        } catch (Exception e) {
            logger.error("添加用户搜藏异常",e);
            resMsg.setCode(CommonConsts.SERVER_ERROR);
            resMsg.setDes("服务异常");
        }
        return resMsg;
    }

    /**
     * 分页查询搜藏列表
     * @param userId
     * @param bookId
     * @return
     */
    @PostMapping("selectPageList")
    @ResponseBody
    public ResMsg<PageInfo<UsersBookCollect>> selectPageList(Integer userId,Integer bookId,Integer currentPage,Integer pageSize){
        logger.info("查询搜藏列表 userId:"+userId+" bookId:"+bookId);
        ResMsg<PageInfo<UsersBookCollect>> resMsg = new ResMsg<PageInfo<UsersBookCollect>>();
        Map<Integer,User> userMap = new HashMap<Integer,User>();
        Map<Integer,Book> bookMap = new HashMap<Integer,Book>();
        UsersBookCollect queryCollect = new UsersBookCollect();
        queryCollect.setUserId(userId);
        queryCollect.setBookId(bookId);
        queryCollect.setYn(CommonConsts.STAT_OK);
        try {
            resMsg = usersBookCollectService.selectPageInfo(queryCollect,currentPage,pageSize);
            if(!ObjectUtils.isEmpty(resMsg)){
                Integer code = resMsg.getCode();
                if(CommonConsts.SUCCESS.equals(code)){
                    PageInfo<UsersBookCollect> pageInfo = resMsg.getObj();
                    if(!ObjectUtils.isEmpty(pageInfo)){
                        List<UsersBookCollect> list = pageInfo.getList();
                        if(!ObjectUtils.isEmpty(list)){
                            for (UsersBookCollect usersBookCollect : list) {
                                Integer cuserId = usersBookCollect.getUserId();
                                Integer cbookId = usersBookCollect.getBookId();
                                if(!userMap.containsKey(cuserId)){
                                    User user = userService.selectUserByExample(new User(cuserId));
                                    userMap.put(cuserId,user);
                                }
                                if(!bookMap.containsKey(cbookId)){
                                    Book book = bookService.selectSimpleInfoByExample(new Book(cbookId));
                                    bookMap.put(cbookId,book);
                                }
                                usersBookCollect.setUser(userMap.get(cuserId));
                                usersBookCollect.setBook(bookMap.get(cbookId));
                            }
                        }
                    }
                }
            }
            return resMsg;

        } catch (Exception e) {
            logger.error("分页查询搜藏列表异常",e);
            resMsg.setCode(CommonConsts.SERVER_ERROR);
            resMsg.setDes("服务异常");
            return resMsg;
        }
    }

    /**
     * 条件查询数量
     * @param userId
     * @param bookId
     * @return
     */
    @PostMapping("selectCount")
    @ResponseBody
    public ResMsg<Integer> selectCount(Integer userId,Integer bookId){
        logger.info("条件查询数量,memId:"+userId+" bookId:"+bookId);
        ResMsg<Integer> resMsg = new ResMsg<Integer>();
        UsersBookCollect queryParam = new UsersBookCollect();
        queryParam.setUserId(userId);
        queryParam.setBookId(bookId);
        try {
            ResMsg<List<UsersBookCollect>> resMsg1 = usersBookCollectService.selectList(queryParam);
            if(!ObjectUtils.isEmpty(resMsg1)){
                if(CommonConsts.SUCCESS.equals(resMsg1.getCode())){
                    List<UsersBookCollect> list = resMsg1.getObj();
                    resMsg.setCode(CommonConsts.SUCCESS);
                    resMsg.setObj(list==null?0:list.size());
                }else{
                    resMsg.setCode(resMsg1.getCode());
                    resMsg.setDes(resMsg1.getDes());
                }
            }
        } catch (Exception e) {
            logger.error("查询数量异常",e);
            resMsg.setCode(CommonConsts.SERVER_ERROR);
            resMsg.setDes("服务异常");
        }
        return resMsg;
    }

}
