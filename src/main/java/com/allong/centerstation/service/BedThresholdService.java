package com.allong.centerstation.service;

import com.allong.centerstation.domain.BedThresholdDetail;
import com.allong.centerstation.domain.entity.BedThreshold;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 床位报警阀值设置 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
public interface BedThresholdService extends IService<BedThreshold> {

    List<BedThresholdDetail> listDetails();
}
