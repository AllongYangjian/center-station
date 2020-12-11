package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.Patient;
import com.allong.centerstation.mapper.PatientMapper;
import com.allong.centerstation.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 病人信息表 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Override
    public List<Patient> listByHid(Integer hid) {
        return baseMapper.selectList(new QueryWrapper<Patient>().eq("hid", hid));
    }
}
