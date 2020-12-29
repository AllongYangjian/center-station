package com.allong.centerstation.domain;

import com.allong.centerstation.domain.entity.HospitalBed;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: BedDeital
 * Author:   YJ
 * Date:     2020/12/25 13:40
 * Description: 床位详情
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/25 13:40        1.0              描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BedDetail extends HospitalBed {
    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 是否选择
     */
    private boolean selected;

    private boolean alarmed;

}
