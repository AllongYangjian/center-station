package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.BedThresholdDetail;
import com.allong.centerstation.domain.entity.BedThreshold;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 床位报警阀值设置 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
public interface BedThresholdMapper extends BaseMapper<BedThreshold> {

    List<BedThresholdDetail> listDetails(Integer hid);
}
