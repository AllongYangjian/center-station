package com.allong.centerstation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.allong.centerstation.domain.BaseEntity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 病人信息表
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_patient")
public class Patient extends BaseEntity<Patient> {

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

    /**
     * PID
     */
    private String pid;

    /**
     * 住院号
     */
    private String zyh;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 主述
     */
    private String complaint;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordTime;

    /**
     * 记录人
     */
    private String recordUser;

    /**
     * 状态 1-可查看 0 不可查看
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
