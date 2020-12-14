package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.allong.centerstation.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨建
 * @since 2020-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_info")
public class UserInfo extends BaseEntity<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 性别 1-男，2-女 3-未知
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 所属医院
     */
    private Integer hid;

    /**
     * 科室
     */
    private Integer deptId;


    @Override
    protected Serializable pkVal() {
        return userId;
    }

}
