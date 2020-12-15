package com.allong.centerstation.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: DateUtils
 * Author:   YJ
 * Date:     2020/12/15 15:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/15 15:04        1.0              描述
 */
public class DateUtils {

    public static Date getTodayStartTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }
}
