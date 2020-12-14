package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 模板详情
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template_detail")
public class TemplateDetail extends BaseEntity<TemplateDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    private Integer tempId;

    /**
     * 关键字id
     */
    private Integer keyId;

    /**
     * 类型 1-波形 2-数字
     */
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}