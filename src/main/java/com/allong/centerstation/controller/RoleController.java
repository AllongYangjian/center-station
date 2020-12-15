package com.allong.centerstation.controller;

import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    @Log("查询角色列表")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(roleService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存角色信息")
    public ResponseEntity<Object> save(@RequestBody Role role) {
        return new ResponseEntity<>(new Result.Builder<>().setData(role.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    @Log("更新角色信息")
    public ResponseEntity<Object> update(@RequestBody Role role) {
        return new ResponseEntity<>(new Result.Builder<>().setData(role.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Log("删除指定角色信息")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(roleService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    @Log("删除所有角色信息")
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(roleService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

