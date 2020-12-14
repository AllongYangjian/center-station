package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

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
