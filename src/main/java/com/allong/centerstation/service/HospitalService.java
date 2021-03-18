package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.Hospital;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 医院信息 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface HospitalService extends IService<Hospital> {

    boolean cascadeDelete(Integer hospitalId);
}
