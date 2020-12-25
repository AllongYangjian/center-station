package com.allong.centerstation.service;

import com.allong.centerstation.domain.BedDetail;
import com.allong.centerstation.domain.entity.UserBed;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户床位信息 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-25
 */
public interface UserBedService extends IService<UserBed> {

    boolean saveUserBedList(List<BedDetail> beds);
}
