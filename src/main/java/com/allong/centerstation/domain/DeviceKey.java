package com.allong.centerstation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.allong.centerstation.domain.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备关键字
 * </p>
 *
 * @author 杨建
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_device_key")
public class DeviceKey extends BaseEntity<DeviceKey> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
