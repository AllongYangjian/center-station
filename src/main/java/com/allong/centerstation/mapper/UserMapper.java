package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.UserDetailInfo;
import com.allong.centerstation.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface UserMapper extends BaseMapper<User> {

    User queryByUsername(String username);

    UserDetailInfo queryUserDetailInfo(String username);

}
