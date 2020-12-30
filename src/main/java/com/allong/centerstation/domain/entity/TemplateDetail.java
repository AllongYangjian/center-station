package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 模板详情
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template_detail")
public class TemplateDetail extends BaseEntity<TemplateDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板id
     */
    private Integer tempId;

    /**
     * 设备类型id
     */
    private Integer deviceId;

    /**
     * 关键字id
     */
    private Integer keyId;

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
     * 1-波形字段 2-数值字段
     */
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
