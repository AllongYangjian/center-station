package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.TemplateGroup;
import com.allong.centerstation.service.TemplateDetailService;
import com.allong.centerstation.service.TemplateGroupService;
import com.allong.centerstation.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class TemplateGroupController {

    @Autowired
    private TemplateGroupService templateGroupService;

    @Autowired

    private TemplateDetailService templateDetailService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> list(@PathVariable("username") String username) {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.listByUsername(username)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody TemplateGroup templateGroup) {
        templateGroup.setUsername(SecurityUtils.getCurrentUsername());
        boolean result = templateGroup.insert();
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.getId()).buildSaveSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.insert()).buildSaveSuccess(), HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody TemplateGroup templateGroup) {
        boolean result = templateGroup.updateById();
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.getId()).buildUpdateSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().setData(templateGroup.getId()).buildUpdateFailed(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        templateDetailService.deleteByTempId(id);
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateGroupService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

