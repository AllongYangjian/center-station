package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.TemplateGroup;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.TemplateDetailService;
import com.allong.centerstation.service.TemplateGroupService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 模板名称 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/template")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEMPLATE')")
public class TemplateGroupController {

    @Autowired
    private TemplateGroupService templateGroupService;

    @Autowired

    private TemplateDetailService templateDetailService;

    @GetMapping
    @Log("获取模板列表")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/enable")
    @Log("获取可用模板信息")
    public ResponseEntity<Object> selectEnable() {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.getOne(new QueryWrapper<TemplateGroup>()
                .eq("enable", true))).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @Log("获取用户自定义模板列表")
    public ResponseEntity<Object> list(@PathVariable("username") String username) {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.listByUsername(username)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存模板信息")
    public ResponseEntity<Object> save(@RequestBody TemplateGroup templateGroup) {
        templateGroup.setUsername(SecurityUtils.getCurrentUsername());
        boolean result = templateGroup.insert();
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.getId()).buildSaveSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.insert()).buildSaveSuccess(), HttpStatus.OK);
        }
    }

    @PutMapping("/{tempId}")
    @Log("启用指定模板")
    public ResponseEntity<Object> update(@PathVariable("tempId") Integer tempId) {
        templateGroupService.updateAllDisable();
        boolean result = templateGroupService.updateOneEnable(tempId);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildUpdateSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildUpdateFailed(), HttpStatus.OK);
        }

    }

    @PutMapping
    @Log("更新指定模板信息")
    public ResponseEntity<Object> update(@RequestBody TemplateGroup templateGroup) {
        boolean result = templateGroup.updateById();
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.getId()).buildUpdateSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.getId()).buildUpdateFailed(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    @Log("删除指定模板")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        templateDetailService.deleteByTempId(id);
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    @Log("删除所有模板")
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

