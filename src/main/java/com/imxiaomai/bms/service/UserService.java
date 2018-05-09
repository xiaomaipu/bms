package com.imxiaomai.bms.service;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.BorrowBook;
import com.imxiaomai.bms.entity.User;

import java.util.List;

/**
 * Created by hyy on 2018/1/3.
 */
public interface UserService {

    /**
     * 查询用户列表
     * @param param
     * @return
     */
    List<User> selectListByExample(User param) throws Exception;

    /**
     * 添加或修改用户信息,用户名必传,且用户名不能重复
     * @param user
     * @return
     */
    User saveOrUpdate(User user) throws Exception;

    /**
     * 分页查询
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> selectPageList(User user,Integer pageNum,Integer pageSize) throws Exception;


    /**
     * 条件查询用户信息
     * @param user
     * @return
     * @throws Exception
     */
    User selectUserByExample(User user) throws Exception;

    /**
     * 根据用户id获取书籍id查询读后感详情列表
     * @param bookUserRecord
     * @return
     */
    List<BookUserRecord> selectRecordDetailListByExample(BookUserRecord bookUserRecord) throws Exception;

    /**
     * 根据用户id获取书籍id查询读后感详情分页
     * @param bookUserRecord
     * @return
     */
    PageInfo<BookUserRecord> selectRecordDetailPageByExample(BookUserRecord bookUserRecord,Integer pageNum,Integer pageSize) throws Exception;

    /**
     * 根据账号查询用户信息
     * @param account 用户名或手机号
     * @return
     */
    User selectByAccount(String account) throws Exception;

    /**
     * 用户登陆成功后通过该方法保存登陆用户信息
     * @param token 用户登陆凭证
     * @param user  用户信息
     */
    void  cacheUserInfo(String token,User user);
}
