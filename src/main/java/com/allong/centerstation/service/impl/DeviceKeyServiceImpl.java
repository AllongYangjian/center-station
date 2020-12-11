package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.KeyDetail;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.allong.centerstation.mapper.DeviceKeyMapper;
import com.allong.centerstation.service.DeviceKeyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备关键字 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class DeviceKeyServiceImpl extends ServiceImpl<DeviceKeyMapper, DeviceKey> implements DeviceKeyService {

    @Override
    public List<DeviceKey> listByDeviceId(Integer deviceId) {
        return baseMapper.selectList(new QueryWrapper<DeviceKey>().eq("device_id", deviceId));
    }

    @Override
    public List<KeyDetail> listKeyDetail() {
        return baseMapper.listKeyDetail();
    }
}
