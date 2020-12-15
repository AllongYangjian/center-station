package com.allong.centerstation.logger.type;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: LogActionType
 * Author:   YJ
 * Date:     2020/11/11 16:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 16:02        1.0              描述
 */
public enum  LogActionType {

    /**
     * 增删改查
     */
    ADD("新增"),
    SELECT("查询"),
    UPDATE("更新"),
    DELETE("删除");
    private String value;

    LogActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
