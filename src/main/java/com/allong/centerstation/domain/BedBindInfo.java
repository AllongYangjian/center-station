package com.allong.centerstation.domain;

import com.allong.centerstation.domain.entity.BedPatient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: BedBindInfo
 * Author:   YJ
 * Date:     2020/12/25 16:32
 * Description: 床位捆绑信息
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/25 16:32        1.0              描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BedBindInfo extends BedPatient {
    /**
     * 医院id
     */
    private Integer hospitalId;
    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 床号
     */
    private String bed;
    /**
     * 病人名称
     */
    private String name;
    /**
     * 病人住院号
     */
    private String zyh;

}
