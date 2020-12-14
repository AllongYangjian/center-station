package com.allong.centerstation.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: UserInfo
 * Author:   YJ
 * Date:     2020/12/14 13:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/14 13:47        1.0              描述
 */
@Data
@EqualsAndHashCode
public class UserDetailInfo {

    private String username;

    private String password;

    private Integer hid;

    private String name;

    private Integer gender;

    private Integer age;
}
