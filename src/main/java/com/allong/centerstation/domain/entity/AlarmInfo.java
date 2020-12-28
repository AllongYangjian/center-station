package com.allong.centerstation.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.allong.centerstation.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 报警信息
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_alarm_info")
public class AlarmInfo extends BaseEntity<AlarmInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 报警病人
     */
    private String pid;

    /**
     * 报警类型
     */
    private String alarmType;

    /**
     * 报警关键字
     */
    private String alarmKey;

    /**
     * 报警值
     */
    private String alarmValue;

    private String min;

    private String max;

    /**
     * 报警内容
     */
    private String alarmMsg;

    /**
     * 报警时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Date alarmTime;

    /**
     * 1-已处理 0-未处理
     */
    private Boolean handle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    private String handleUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
