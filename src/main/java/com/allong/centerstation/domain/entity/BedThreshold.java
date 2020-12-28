package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.allong.centerstation.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 床位报警阀值设置
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_bed_threshold")
public class BedThreshold extends BaseEntity<BedThreshold> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 床位id
     */
    private Integer patientId;

    /**
     * 关键字id
     */
    private Integer keyId;

    /**
     * 下限
     */
    private String min;

    /**
     * 上线
     */
    private String max;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String createUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
