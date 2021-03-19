package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.UserDetailInfo;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.mapper.UserMapper;
import com.allong.centerstation.service.RoleService;
import com.allong.centerstation.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user!=null){
            user.setRoles(roleService.selectAllByUserId(user.getId()));
        }
        return user;
    }

    @Override
    public UserDetailInfo queryUserDetailInfo(String username) {
        return baseMapper.queryUserDetailInfo(username);
    }
}
