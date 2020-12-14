package com.allong.centerstation.utils;

import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            if (user == null) {
                return "";
            }
            return user.getUsername();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "";
    }

    public static List<Role> getRoles() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            if (user == null) {
                return new ArrayList<>();
            }
            return user.getRoles();
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }
}
