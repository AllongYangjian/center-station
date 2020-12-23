package com.allong.centerstation.service;

import com.allong.centerstation.domain.PatientDetail;
import com.allong.centerstation.domain.entity.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 病人信息表 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface PatientService extends IService<Patient> {

    List<Patient> listByHid(Integer hid);

    List<PatientDetail> listDetail(Integer status);

    List<PatientDetail> listDetailByHid(Integer hid,Integer status);
}
