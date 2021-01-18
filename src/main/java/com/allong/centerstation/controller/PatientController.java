package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Patient;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.PatientService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PATIENT')")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserInfoService infoService;

    @GetMapping("/status/{status}")
    @Log("查询病人列表")
    public ResponseEntity<Object> listByType(@PathVariable("status")Integer status) {
        //管理员，获取所有病人， 其他人，获取自己病人的病人
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
            return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listDetail(status)).buildQuerySuccess(), HttpStatus.OK);
        } else {
            UserInfo userInfo = infoService.loadUserByUserId(SecurityUtils.getCurrentUsername());
            return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listDetailByHid(userInfo.getHid(),status)).buildQuerySuccess(), HttpStatus.OK);
        }
    }

    @GetMapping("/{hid}")
    @Log("查询病人下属病人列表")
    public ResponseEntity<Object> list(@PathVariable("hid") Integer hid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listByHid(hid)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存病人信息")
    public ResponseEntity<Object> save(@RequestBody Patient patient) {
        patient.setRecordTime(new Date());
        patient.setRecordUser(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(new Result.Builder<>().setData(patient.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    @Log("更新病人信息")
    public ResponseEntity<Object> update(@RequestBody Patient patient) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patient.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Log("删除病人信息")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    @Log("删除所有病人信息")
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

