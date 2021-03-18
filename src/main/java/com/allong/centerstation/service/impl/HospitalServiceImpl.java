package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.Hospital;
import com.allong.centerstation.mapper.HospitalMapper;
import com.allong.centerstation.service.DeptService;
import com.allong.centerstation.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 医院信息 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
@Transactional
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {

    @Autowired
    private DeptService deptService;

    @Override
    public boolean cascadeDelete(Integer hospitalId) {
        deptService.removeAllByHid(hospitalId);
        return removeById(hospitalId);
    }
}
