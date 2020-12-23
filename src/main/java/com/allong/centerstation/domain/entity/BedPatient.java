package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.allong.centerstation.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_bed_patient")
public class BedPatient extends BaseEntity<BedPatient> {

    private static final long serialVersionUID = 1L;

    /**
     * 医院床位表主键
     */
    private Integer bedId;

    /**
     * 病人表主键
     */
    private Integer patientId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
