package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Patient;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.service.PatientService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 病人信息表 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserInfoService infoService;

    @GetMapping
    public ResponseEntity<Object> list() {
        //管理员，获取所有病人， 其他人，获取自己医院的病人
        List<Role> roles = SecurityUtils.getRoles();//如果是管理员
        boolean contain = false;
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                if (role.getRoleName().toUpperCase().contains("ADMIN")) {
                    contain = true;
                    break;
                }
            }
        }
        if (contain) {
            return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listDetail()).buildQuerySuccess(), HttpStatus.OK);
        } else {
            UserInfo userInfo = infoService.loadUserByUserId(SecurityUtils.getCurrentUsername());
            return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listDetailByHid(userInfo.getHid())).buildQuerySuccess(), HttpStatus.OK);
        }
    }

    @GetMapping("/{hid}")
    public ResponseEntity<Object> list(@PathVariable("hid") Integer hid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listByHid(hid)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Patient patient) {
        patient.setRecordTime(new Date());
        patient.setRecordUser(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(new Result.Builder<>().setData(patient.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Patient patient) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patient.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

