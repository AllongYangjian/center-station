package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.KeyDetail;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 设备关键字 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface DeviceKeyMapper extends BaseMapper<DeviceKey> {

    List<KeyDetail> listKeyDetail();
}
