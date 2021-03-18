package com.allong.centerstation.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C), 2015-2021, 杭州奥朗信息科技有限公司
 * FileName: BedCriteria
 * Author:   YJ
 * Date:     2021/3/18 16:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2021/3/18 16:52        1.0              描述
 */
@Data
public class BedCriteria implements Serializable {
    /**
     * 开始
     */
    private Integer start;
    /**
     * 结束
     */
    private Integer end;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 后缀
     */
    private String suffix;
    /**
     *  长度
     */
    private Integer length;
    /**
     * 过滤
     */
    private String filter;
}
