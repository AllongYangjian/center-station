package com.allong.centerstation.domain;

import com.allong.centerstation.domain.entity.UserInfo;
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
public class UserDetailInfo extends UserInfo {

//    private String username;

    private String password;

    private String hospitalName;

    private String deptCode;

    private String deptName;

}
