package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectAllByUserId(Integer uid);
}
