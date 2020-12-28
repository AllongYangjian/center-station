package com.allong.centerstation.domain;

import com.allong.centerstation.domain.entity.BedThreshold;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: BedThresholdDetail
 * Author:   YJ
 * Date:     2020/12/28 11:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/28 11:25        1.0              描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BedThresholdDetail extends BedThreshold {

    /**
     * 医院id
     */
    private String hid;
    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 科室
     */
    private String dept;
    /**
     * 病人姓名
     */
    private String name;

    private String keyCode;

    private String keyName;

    private String min;

    private String max;
}
