package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.Patient;
import com.allong.centerstation.mapper.PatientMapper;
import com.allong.centerstation.service.PatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
