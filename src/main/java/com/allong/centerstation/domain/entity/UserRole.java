package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
public class UserRole extends BaseEntity<UserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 用户id
     */
    private Integer uid;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
