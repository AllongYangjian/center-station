package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface RoleService extends IService<Role> {
    /**
     * 获取用户授权的角色列表
     *
     * @param uid
     * @return
     */
    List<Role> selectAllByUserId(Integer uid);
}
