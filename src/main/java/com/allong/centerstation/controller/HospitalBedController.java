package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.HospitalBed;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.HospitalBedService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 医院床位列表 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/api/bed")
public class HospitalBedController {

    @Autowired
    private HospitalBedService bedService;

    @GetMapping
    @Log("查询床位列表")
    public ResponseEntity<Object> list(){
        return new ResponseEntity<>(new Result.Builder<>().setData(bedService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{hid}")
    @Log("查询指定医院的床位列表")
    public ResponseEntity<Object> listByHid(@PathVariable("hid")Integer hid){
        return new ResponseEntity<>(new Result.Builder<>().setData(bedService.list(new QueryWrapper<HospitalBed>()
                .eq("hid",hid))).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存床位信息")
    public ResponseEntity<Object> save(@RequestBody HospitalBed bed){
        boolean result = bed.insert();
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildSaveFailed(), HttpStatus.OK);
        }

    }

    @PutMapping
    @Log("更新床位信息")
    public ResponseEntity<Object> update(@RequestBody HospitalBed bed){
        boolean result = bed.updateById();
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildUpdateSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildUpdateFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Log("更新床位信息")
    public ResponseEntity<Object> delete(@PathVariable("id")Integer id){
        boolean result = bedService.removeById(id);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildDeleteSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildDeleteFailed(), HttpStatus.OK);
        }
    }


}
