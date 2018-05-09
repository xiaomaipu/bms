package com.imxiaomai.bms.mapper;

import com.imxiaomai.bms.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by hyy on 2018/1/2.
 */
public interface UserMapper extends Mapper<User>{

    List<User> selectLoginUser(String userName);

    int insertUser(User user);

}
