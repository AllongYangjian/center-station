package com.allong.centerstation.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: ThrowableUtil
 * Author:   YJ
 * Date:     2020/11/12 15:59
 * Description: 异常工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/12 15:59        1.0              描述
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
