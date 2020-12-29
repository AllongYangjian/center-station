package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.BedAlarmConfig;
import com.allong.centerstation.mapper.BedAlarmConfigMapper;
import com.allong.centerstation.service.BedAlarmConfigService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 床位报警配置 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@Service
public class BedAlarmConfigServiceImpl extends ServiceImpl<BedAlarmConfigMapper, BedAlarmConfig> implements BedAlarmConfigService {

    @Override
    public boolean updateByBedId(Integer id) {
        BedAlarmConfig config = baseMapper.selectOne(new QueryWrapper<BedAlarmConfig>().eq("bed_id",id));
        if(config ==null){
            return false;
        }else {
            config.setEnable(!config.getEnable());
            config.setUpdateTime(new Date());
            config.setUpdateUser(SecurityUtils.getCurrentUsername());
            return config.updateById();
        }
    }
}
