package com.allong.centerstation.utils;

import com.allong.centerstation.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: SecurityUtils
 * Author:   YJ
 * Date:     2020/12/9 15:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/9 15:12        1.0              描述
 */
public class SecurityUtils {

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return "";
        }
        return user.getUsername();
    }
}
