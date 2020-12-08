package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.User;
import com.allong.centerstation.mapper.UserMapper;
import com.allong.centerstation.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
