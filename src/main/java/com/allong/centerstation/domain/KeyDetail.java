package com.allong.centerstation.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: KeyDeital
 * Author:   YJ
 * Date:     2020/12/11 13:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/11 13:35        1.0              描述
 */
@Data
@EqualsAndHashCode
public class KeyDetail {

    private Integer keyId;

    private String deviceName;

    private String keyName;

    private Boolean wave;
}
