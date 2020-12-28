package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.BedDetail;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.UserBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户床位信息 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/user/bed")
public class UserBedController {

    @Autowired
    private UserBedService service;

    @PostMapping
    @Log("保存用户床位信息")
    public ResponseEntity<Object> saveList(@RequestBody List<BedDetail> beds){
        boolean result = service.saveUserBedList(beds);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildQueryFailed(), HttpStatus.OK);
        }
    }


    @GetMapping("/patient")
    @Log("加载用户床位绑定的病人列表")
    public ResponseEntity<Object> queryUserBedPatientList(){
        return new ResponseEntity<>(new Result.Builder<>().setData(service.queryUserBedPatientList()).buildQuerySuccess(),HttpStatus.OK);
    }

}

