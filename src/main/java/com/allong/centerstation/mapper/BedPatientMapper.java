package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.BedBindInfo;
import com.allong.centerstation.domain.entity.BedPatient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
public interface BedPatientMapper extends BaseMapper<BedPatient> {

    List<BedBindInfo> selectBedBindInfoList(Integer hid);
}
