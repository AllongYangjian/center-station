package com.allong.centerstation.domain;

import com.allong.centerstation.domain.entity.Patient;
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
@EqualsAndHashCode(callSuper = true)
public class PatientDetail extends Patient  {


    /**
     * 医院名称
     */

    private String hospitalName;
    /**
     * 床位id
     */
    private Integer bedId;
}
