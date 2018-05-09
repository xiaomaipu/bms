package com.imxiaomai.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.common.Constants;
import com.imxiaomai.bms.entity.BookUserRecord;
import com.imxiaomai.bms.entity.BorrowBook;
import com.imxiaomai.bms.entity.User;
import com.imxiaomai.bms.mapper.BookUserRecordMapper;
import com.imxiaomai.bms.mapper.UserMapper;
import com.imxiaomai.bms.service.UserService;
import com.imxiaomai.bms.util.CommonConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hyy on 2018/1/3.
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private BookUserRecordMapper bookUserRecordMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<User> selectListByExample(User param) throws Exception{
        if(param == null){
            param = new User();
        }
        logger.info("查询用户列表,参数 "+JSON.toJSONString(param));

        param.setYn(1);
        PageHelper.orderBy("created desc");
        List<User> list = userMapper.select(param);
        return list;
    }

    @Override
    @Transactional
    public User saveOrUpdate(User user) throws Exception{
        logger.info("添加或修改用户信息,参数 "+ JSON.toJSONString(user));
        if(!ObjectUtils.isEmpty(user)){
            String userName = user.getUserName();
            if(ObjectUtils.isEmpty(userName)){
                throw new RuntimeException("用户名必须传递");
            }
            User queryUser = new User();
            queryUser.setUserName(userName);
            User resUser = selectUserByExample(queryUser);
            Integer userId = user.getId();

            if(!ObjectUtils.isEmpty(resUser)){
                if(userId == null){
                    throw new RuntimeException("用户名:"+userName+" 已经使用,请更换用户名");
                }else{
                    if(resUser.getId() != userId){
                        throw new RuntimeException("用户名:"+userName+" 已经使用,请更换用户名");
                    }
                }
            }
            if(ObjectUtils.isEmpty(userId)){
                logger.info("添加用户信息");
                user.setCreated(new Date());
                userMapper.insertUser(user);
            }else{
                logger.info("修改用户信息");
                user.setUpdated(new Date());
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        return user;
    }

    @Override
    public PageInfo<User> selectPageList(User user, Integer pageNum, Integer pageSize) throws Exception{
        logger.info("分页查询用户信息");
        if(user == null){
            user = new User();
        }
        user.setYn(1);
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("created desc");
        List<User> list = userMapper.select(user);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    @Override
    public User selectUserByExample(User user) throws Exception {
        logger.info("查询用户信息,参数 "+JSON.toJSONString(user));
        if(!ObjectUtils.isEmpty(user)){
            user.setYn(1);
            User res = userMapper.selectOne(user);
            return res;
        }
        return null;
    }

    @Override
    public List<BookUserRecord> selectRecordDetailListByExample(BookUserRecord bookUserRecord) throws Exception{
        logger.info("查询读后感详细信息列表,参数 "+JSON.toJSONString(bookUserRecord));
        if(bookUserRecord == null){
            bookUserRecord = new BookUserRecord();
        }
        List<BookUserRecord> list = bookUserRecordMapper.selectDetailListByExample(bookUserRecord);
        return list;
    }

    @Override
    public PageInfo<BookUserRecord> selectRecordDetailPageByExample(BookUserRecord bookUserRecord,Integer pageNum,Integer pageSize) throws Exception{
        logger.info("分页查询读后感详细信息,参数 "+JSON.toJSONString(bookUserRecord));
        if(bookUserRecord == null){
            bookUserRecord = new BookUserRecord();
        }
        if(pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<BookUserRecord> list = bookUserRecordMapper.selectDetailListByExample(bookUserRecord);
        PageInfo<BookUserRecord> pageInfo = new PageInfo<BookUserRecord>(list);
        return pageInfo;
    }

    @Override
    public User selectByAccount(String account) throws Exception{
        logger.info("根据账号或密码查询用户信息,参数 account:"+account);
        if(StringUtils.isEmpty(account)){
            return null;
        }
        List<User> list = userMapper.selectLoginUser(account);
        if(!ObjectUtils.isEmpty(list)){
            User user = list.get(0);
            return user;
        }
        return null;
    }

    @Override
    public void cacheUserInfo(String token, User user) {
        Assert.hasText(token,"无效的token,token不能为空");
        Assert.notNull(user,"用户信息不能为空");
        redisTemplate.opsForValue().set(Constants.KEY_REDIS_PREFIX+token,user);
    }

}
