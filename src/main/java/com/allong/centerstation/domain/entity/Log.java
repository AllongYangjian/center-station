package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log")
public class Log extends BaseEntity<Log> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    private String description;

    private String logType;

    private String method;

    private String params;

    private String requestIp;

    private Long time;

    private String username;

    private String address;

    private String browser;

    private String exceptionDetail;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Log() {
    }

    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
        this.createTime = new Date();
    }
    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

}
