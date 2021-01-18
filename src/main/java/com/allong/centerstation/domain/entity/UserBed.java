package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.allong.centerstation.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户床位信息
 * </p>
 *
 * @author 杨建
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_bed")
public class UserBed extends BaseEntity<UserBed> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 床位id
     */
    private Integer bedId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
