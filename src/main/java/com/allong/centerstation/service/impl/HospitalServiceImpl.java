package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.Hospital;
import com.allong.centerstation.mapper.HospitalMapper;
import com.allong.centerstation.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 医院信息 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {

}
