package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.mapper.UserInfoMapper;
import com.allong.centerstation.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-14
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfo loadUserByUserId(String userId) {
        return baseMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userId));
    }
}
