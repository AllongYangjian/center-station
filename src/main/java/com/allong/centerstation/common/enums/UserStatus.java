package com.allong.centerstation.common.enums;

import lombok.Getter;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: UserStatus
 * Author:   YJ
 * Date:     2020/12/10 15:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/10 15:32        1.0              描述
 */
@Getter
public enum UserStatus {
    Enable(1, "正常"),
    AccountExpired(2, "账号过期"),
    AccountLock(3, "账号锁定"),
    CredentialExpired(4, "密码过期")
    ;
    private Integer code;
    private String name;

    UserStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
