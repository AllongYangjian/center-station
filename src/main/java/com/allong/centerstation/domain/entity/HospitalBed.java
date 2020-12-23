package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.allong.centerstation.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 医院床位列表
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_hospital_bed")
public class HospitalBed extends BaseEntity<HospitalBed> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 医院id
     */
    private Integer hid;

    /**
     * 床号
     */
    private String bed;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
