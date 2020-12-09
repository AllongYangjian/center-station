package com.allong.centerstation.service;

import com.allong.centerstation.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface UserService extends IService<User> , UserDetailsService {

}