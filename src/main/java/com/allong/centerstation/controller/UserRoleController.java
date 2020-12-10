package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.Hospital;
import com.allong.centerstation.domain.UserRole;
import com.allong.centerstation.service.HospitalService;
import com.allong.centerstation.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping
    public ResponseEntity<Object> saveAll(@RequestBody List<UserRole> userRoleList) {
        if (userRoleList.size() > 0) {
            //先删除所有的数据在保存
            userRoleService.removeByUid(userRoleList.get(0).getUid());
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(userRoleService.saveBatch(userRoleList)).buildSaveSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Object> delete(@PathVariable("uid") Integer uid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(userRoleService.removeByUid(uid)).buildDeleteSuccess(), HttpStatus.OK);
    }

}

