package com.allong.centerstation.domain.entity;

import com.allong.centerstation.common.enums.UserStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseEntity<User> implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 1-正常 2-无效 3-锁定 4-过期
     */
    private Integer status;

    @TableField(exist = false)
    private List<Role> roles;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return !status.equals(UserStatus.AccountExpired.getCode());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !status.equals(UserStatus.AccountLock.getCode());
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return !status.equals(UserStatus.CredentialExpired.getCode());
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return status.equals(UserStatus.Enable.getCode());
    }
}
