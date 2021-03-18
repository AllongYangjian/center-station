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
 * 
 * </p>
 *
 * @author 杨建
 * @since 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept_bed")
public class DeptBed extends BaseEntity<DeptBed> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 医院id
     */
    private Integer hospitalId;

    /**
     * 科室id
     */
    private Integer deptId;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 床标号
     */
    private String bedLabel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
