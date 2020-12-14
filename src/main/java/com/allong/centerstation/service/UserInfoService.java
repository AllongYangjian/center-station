package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-14
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo loadUserByUserId(String userId);
}
