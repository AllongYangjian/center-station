package com.allong.centerstation.controller;

import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Dept;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2021-03-18
 */
@RestController
@RequestMapping("/api/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/list/{hid}")
    @Log("查询科室列表")
    public ResponseEntity<Object> list(@PathVariable("hid") Integer hid) {
        //如果是管理员，则显示全部科室
        return new ResponseEntity<>(new Result.Builder<>().setData(deptService.listByHid(hid)).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{deptCode}")
    @Log("根据科室代码查询科室信息")
    public ResponseEntity<Object> list(@PathVariable("deptCode") String deptCode) {
        //如果是管理员，则显示全部科室
        return new ResponseEntity<>(new Result.Builder<>().setData(deptService.getByDeptCode(deptCode)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存科室信息")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL','ROLE_PATIENT')")
    public ResponseEntity<Object> save(@RequestBody Dept dept) {

        Dept cache = deptService.getByDeptCode(dept.getDeptCode());
        if (cache != null) {
            return new ResponseEntity<>(new Result.Builder<>().setMessage("该科室已存在").buildSaveFailed(), HttpStatus.OK);
        }
        boolean result = deptService.save(dept);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveFailed(), HttpStatus.OK);
        }
    }

    @PutMapping
    @Log("更新科室信息")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL','ROLE_PATIENT')")
    public ResponseEntity<Object> update(@RequestBody Dept dept) {
//        Dept cache = deptService.getByDeptCode(dept.getDeptCode());
//        if (cache != null) {
//            return new ResponseEntity<>(new Result.Builder<>().setMessage("该科室已存在").buildUpdateFailed(), HttpStatus.OK);
//        }
        boolean result = dept.updateById();
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildUpdateSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildUpdateFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{deptId}")
    @Log("删除指定科室信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("deptId") Integer deptId) {
        boolean result = deptService.cascadeDelete(deptId);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/all/{hid}")
    @Log("删除所有科室信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteAll(@PathVariable("hid") Integer hid) {
        boolean result = deptService.removeAllByHid(hid);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteFailed(), HttpStatus.OK);
        }
    }
}

