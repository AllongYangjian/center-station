package com.allong.centerstation.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: RequestHolder
 * Author:   YJ
 * Date:     2020/11/11 16:19
 * Description: 获取当前的 request请求
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 16:19        1.0              描述
 */
public class RequestHolder {
    /**
     * 从 RequestContextHolder 得到当前请求的 HttpServletRequest
     * @return  HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(requestAttributes).getRequest();
    }
}
