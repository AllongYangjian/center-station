package com.allong.centerstation.logger.annotation;


import com.allong.centerstation.logger.type.LogActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: Log
 * Author:   YJ
 * Date:     2020/11/11 16:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 16:03        1.0              描述
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String value() default "";

    boolean enable() default true;

    LogActionType type() default LogActionType.SELECT;
}
