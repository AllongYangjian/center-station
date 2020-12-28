package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.BedThreshold;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.BedThresholdService;
import com.allong.centerstation.utils.SecurityUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 床位报警阀值设置 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@RestController
@RequestMapping("/api/bedThreshold")
public class BedThresholdController {

    @Autowired
    private BedThresholdService bedThresholdService;

    @GetMapping
    @Log("查询床位报警阀值")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(bedThresholdService.listDetails()).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存床位报警阀值")
    public ResponseEntity<Object> save(@RequestBody BedThreshold bedThreshold) {
        bedThreshold.setEnable(true);
        bedThreshold.setCreateTime(new Date());
        bedThreshold.setCreateUser(SecurityUtils.getCurrentUsername());
        boolean result = bedThresholdService.save(bedThreshold);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildSaveFailed(), HttpStatus.OK);
        }

    }

    @PutMapping
    @Log("更新床位报警阀值")
    public ResponseEntity<Object> update(@RequestBody BedThreshold bedThreshold) {
        bedThreshold.setUpdateTime(new Date());
        bedThreshold.setUpdateUser(SecurityUtils.getCurrentUsername());
        boolean result = bedThresholdService.updateById(bedThreshold);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildUpdateSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildUpdateFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Log("更新床位报警阀值")
    public ResponseEntity<Object> delete(@PathVariable("id")Integer id) {
        boolean result = bedThresholdService.removeById(id);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildDeleteSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildDeleteFailed(), HttpStatus.OK);
        }
    }
}

