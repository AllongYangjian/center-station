package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.BedDetail;
import com.allong.centerstation.domain.entity.HospitalBed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 医院床位列表 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
public interface HospitalBedMapper extends BaseMapper<HospitalBed> {


    List<BedDetail> listBedDetailByHid(Integer hid);
}
