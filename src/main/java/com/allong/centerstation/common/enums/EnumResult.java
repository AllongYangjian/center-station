package com.allong.centerstation.common.enums;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: EnumResult
 * Author:   YJ
 * Date:     2020/11/19 11:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/19 11:11        1.0              描述
 */
public enum EnumResult {

    QUERY_SUCCESS(200, "查询成功"),
    QUERY_FAILED(10001, "查询失败"),
    UPDATE_SUCCESS(200, "更新成功"),
    UPDATE_FAILED(10003, "更新失败"),
    SAVE_SUCCESS(200, "保存成功"),
    SAVE_FAILED(10005, "保存失败"),
    DELETE_SUCCESS(200, "删除成功"),
    DELETE_FAILED(10000, "删除失败"),
    OPERATION_SUCCESS(200, "操作成功"),
    OPERATION_FAILED(10008, "操作失败");

    private int code;

    private String desc;

    EnumResult(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
