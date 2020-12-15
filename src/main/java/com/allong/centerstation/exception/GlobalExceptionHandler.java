package com.allong.centerstation.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: ExceptionHandler
 * Author:   YJ
 * Date:     2020/12/15 13:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/15 13:21        1.0              描述
 */
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(AccessDeniedException.class)
//    public String _403() {
//        return "redirect:/error/403";
//    }
//
//
//    @ExceptionHandler(RuntimeException.class)
//    public String _500() {
//        return "redirect:/error/404";
//    }

}
