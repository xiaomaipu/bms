package com.imxiaomai.bms.mapper;

import com.imxiaomai.bms.entity.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role>{

    List<Role> selectUserRoleByUserId(Integer userId);

}