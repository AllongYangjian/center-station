package com.allong.centerstation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: PatientDetail
 * Author:   YJ
 * Date:     2020/12/14 14:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/14 14:48        1.0              描述
 */
@Data
@EqualsAndHashCode
public class PatientDetail implements Serializable {

    private Integer id;

    /**
     * 医院id
     */
    private Integer hid;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 科室
     */
    private String dept;

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

}
