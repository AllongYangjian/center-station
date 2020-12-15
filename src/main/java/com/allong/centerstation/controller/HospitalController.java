package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Hospital;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.HospitalService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.service.UserService;
import com.allong.centerstation.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 医院信息 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private UserInfoService infoService;

    @GetMapping
    @Log("查询医院列表")
    public ResponseEntity<Object> list() {
        //如果是管理员，则显示全部医院
        String username = SecurityUtils.getCurrentUsername();
        if (username == null || username.equals("")) {//表示未登录，注解界面获取
            return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.list()).buildQuerySuccess(), HttpStatus.OK);
        } else {
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
                return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.list()).buildQuerySuccess(), HttpStatus.OK);
            } else {
                //获取用户信息
                UserInfo userInfo = infoService.loadUserByUserId(username);
                List<Integer> list = new ArrayList<>();
                list.add(userInfo.getHid());
                return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.listByIds(list)).buildQuerySuccess(), HttpStatus.OK);
            }
        }
    }

    @PostMapping
    @Log("保存医院信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL','ROLE_PATIENT')")
    public ResponseEntity<Object> save(@RequestBody Hospital hospital) {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospital.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    @Log("更新医院信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL','ROLE_PATIENT')")
    public ResponseEntity<Object> update(@RequestBody Hospital hospital) {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospital.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{hospitalId}")
    @Log("删除指定医院信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("hospitalId") Integer hospitalId) {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.removeById(hospitalId)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    @Log("删除所有医院信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }

}

