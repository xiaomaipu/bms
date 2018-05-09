package com.imxiaomai.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.imxiaomai.bms.entity.UsersDetail;
import com.imxiaomai.bms.mapper.UsersDetailMapper;
import com.imxiaomai.bms.service.UsersDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * Created by hyy on 2018/1/24.
 */
@Service
public class UsersDetailServiceImpl implements UsersDetailService {

    private static Logger logger = LoggerFactory.getLogger(UsersDetailServiceImpl.class);

    @Resource
    private UsersDetailMapper usersDetailMapper;

    @Override
    public UsersDetail selectOneByExample(UsersDetail usersDetail) {
        logger.info("查询用户详情,参数 "+ JSON.toJSONString(usersDetail));
        if(!ObjectUtils.isEmpty(usersDetail)){
            usersDetail.setYn(1);
            usersDetail = usersDetailMapper.selectOne(usersDetail);
        }
        return usersDetail;
    }
}
