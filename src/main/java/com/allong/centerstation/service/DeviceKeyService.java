package com.allong.centerstation.service;

import com.allong.centerstation.domain.KeyDetail;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备关键字 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface DeviceKeyService extends IService<DeviceKey> {

    List<DeviceKey> listByDeviceId(Integer deviceId);

    List<KeyDetail> listKeyDetail();
}
