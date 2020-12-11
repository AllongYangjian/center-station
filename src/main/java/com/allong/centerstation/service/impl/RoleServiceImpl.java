package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.mapper.RoleMapper;
import com.allong.centerstation.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> selectAllByUserId(Integer uid) {
        return baseMapper.selectAllByUserId(uid);
    }
}
