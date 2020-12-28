package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.BedAlarmConfig;
import com.allong.centerstation.domain.entity.BedThreshold;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.BedAlarmConfigService;
import com.allong.centerstation.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 床位报警配置 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/api/bedAlarm")
public class BedAlarmConfigController {

    @Autowired
    private BedAlarmConfigService service;
    

    @GetMapping
    @Log("查询床位报警配置")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(service.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存床位报警配置")
    public ResponseEntity<Object> save(@RequestBody BedAlarmConfig config) {
        config.setCreateTime(new Date());
        config.setCreateUser(SecurityUtils.getCurrentUsername());
        boolean result = service.save(config);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildSaveFailed(), HttpStatus.OK);
        }

    }

    @PutMapping
    @Log("更新床位报警配置")
    public ResponseEntity<Object> update(@RequestBody  BedAlarmConfig config) {
        config.setUpdateTime(new Date());
        config.setUpdateUser(SecurityUtils.getCurrentUsername());
        boolean result = service.updateById(config);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildUpdateSuccess(), HttpStatus.OK);
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

