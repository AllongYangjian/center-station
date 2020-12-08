package com.allong.centerstation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.allong.centerstation.domain.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 模板名称
 * </p>
 *
 * @author 杨建
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template_group")
public class TemplateGroup extends BaseEntity<TemplateGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String username;

    /**
     * 模板名称
     */
    private String tempName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
