package com.allong.centerstation.service;

import com.allong.centerstation.domain.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface UserRoleService extends IService<UserRole> {

    Integer removeByUid(Integer uid);
}
