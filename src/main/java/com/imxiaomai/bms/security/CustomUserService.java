package com.imxiaomai.bms.security;

import com.imxiaomai.bms.entity.Role;
import com.imxiaomai.bms.entity.User;
import com.imxiaomai.bms.mapper.RoleMapper;
import com.imxiaomai.bms.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyy on 2018/1/5.
 */
@Service
public class CustomUserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    private static final String initAdminAccount = "admin";
    private volatile boolean hasInited = false;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("根据userName获取会员信息,参数:"+userName);
//        if(StringUtils.isEmpty(userName)){
//            throw new UsernameNotFoundException("用户未登陆");
//        }
//        List<User> list = userMapper.selectLoginUser(userName);
//        if(ObjectUtils.isEmpty(list)){
//            throw new UsernameNotFoundException("用户"+userName+" 不存在");
//        }
//        User user = list.get(0);
//        List<Role> roleList = roleMapper.selectUserRoleByUserId(user.getId());
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//        if(!ObjectUtils.isEmpty(roleList)){
//            for (Role role : roleList) {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleKey());
//                //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象
//                grantedAuthorities.add(grantedAuthority);
//            }
//        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMIN");
        grantedAuthorities.add(grantedAuthority);
        if (initAdminAccount.equalsIgnoreCase(userName) && !hasInited){
            hasInited = true;
            return new org.springframework.security.core.userdetails.User(initAdminAccount, "13579",grantedAuthorities);
        }

        return null;
    }

}
