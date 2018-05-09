package com.imxiaomai.bms.service;

import com.imxiaomai.bms.entity.UsersDetail;

/**
 * Created by hyy on 2018/1/24.
 */
public interface UsersDetailService {

    /**
     * 查询用户详情
     * @param usersDetail
     * @return
     */
    UsersDetail selectOneByExample(UsersDetail usersDetail);

}
