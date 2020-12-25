package com.allong.centerstation.service;

import com.allong.centerstation.domain.BedBindInfo;
import com.allong.centerstation.domain.entity.BedPatient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
public interface BedPatientService extends IService<BedPatient> {

    List<BedBindInfo> listBedBindInfoList(Integer hid);

    boolean saveBedBindInfo(BedPatient bedPatient);

    boolean updateBedBindInfo(BedPatient bedPatient);
}
