package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.AlarmInfo;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.AlarmInfoService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 报警信息 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/api/alarmInfo")
public class AlarmInfoController {
    @Autowired
    private AlarmInfoService service;


    @GetMapping
    @Log("查询床位报警配置")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(service.list(new QueryWrapper<AlarmInfo>()
        .orderByDesc("alarmTime"))).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存床位报警配置")
    public ResponseEntity<Object> save(@RequestBody AlarmInfo info) {
        info.setAlarmTime(new Date());
        boolean result = service.save(info);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(info.getId()).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildSaveFailed(), HttpStatus.OK);
        }

    }

    @PutMapping
    @Log("更新床位报警配置")
    public ResponseEntity<Object> update(@RequestBody  AlarmInfo info) {
        info.setHandleTime(new Date());
        info.setHandleUser(SecurityUtils.getCurrentUsername());
        boolean result = service.updateById(info);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(info.getId()).buildUpdateSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildUpdateFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Log("更新床位报警配置")
    public ResponseEntity<Object> delete(@PathVariable("id")Integer id) {
        boolean result = service.removeById(id);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildDeleteSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildDeleteFailed(), HttpStatus.OK);
        }
    }
}

