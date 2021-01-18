package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.PatientDetail;
import com.allong.centerstation.domain.entity.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 病人信息表 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface PatientMapper extends BaseMapper<Patient> {

    List<PatientDetail> listDetail(Integer status);

    List<PatientDetail> listDetailByHid(@Param("hid") Integer hid, @Param("status") Integer status);

    List<PatientDetail> listDetailByIds(List<Integer> ids);
}
