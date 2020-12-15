package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Log;
import com.allong.centerstation.service.LogService;
import com.allong.centerstation.utils.DateUtils;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/log")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LOG')")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(logService.list(new QueryWrapper<Log>()
                .eq("username", SecurityUtils.getCurrentUsername())
                .gt("create_time", DateUtils.getTodayStartTime())
                .orderByDesc("create_time")))
                .buildQuerySuccess(), HttpStatus.OK);
    }

}

