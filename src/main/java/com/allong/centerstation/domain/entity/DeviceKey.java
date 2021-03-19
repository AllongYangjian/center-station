package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备关键字
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_device_key")
public class DeviceKey extends BaseEntity<DeviceKey> implements Comparable<DeviceKey>{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 颜色
     */
    private String keyColor;

    /**
     * 大小
     */
    private String keySize;

    /**
     * 下线
     */
    private String min;

    /**
     * 上线
     */
    private String max;

    /**
     * 位置
     */
    private Integer position;

    /**
     * 是否存在波形
     */
    private Boolean wave;

    /**
     * 缩放系数
     */
    private Integer scale;

    /**
     * 单位
     */
    private String unit;

    /**
     * 采样率
     */
    private Integer frameSize;

    /**
     * 最大值
     */
    private Integer maxValue;
    /**
     * 是否启用
     */
    private Boolean enable;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public int compareTo(DeviceKey o) {
        return this.position - o.getPosition();
    }
}
