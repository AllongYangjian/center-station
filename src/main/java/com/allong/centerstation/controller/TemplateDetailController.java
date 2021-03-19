package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.allong.centerstation.domain.entity.TemplateDetail;
import com.allong.centerstation.domain.entity.TemplateGroup;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.DeviceKeyService;
import com.allong.centerstation.service.TemplateDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 模板详情 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/template/detail")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEMPLATE')")
public class TemplateDetailController {

    @Autowired
    private TemplateDetailService templateDetailService;

    @Autowired
    private DeviceKeyService deviceKeyService;

    @GetMapping("/{tempId}")
    @Log("获取指定模板详情")
    public ResponseEntity<Object> list(@PathVariable("tempId") Integer tempId) {
        List<TemplateDetail> templateDetails = templateDetailService.listByTempId(tempId);
        if (templateDetails != null && templateDetails.size() > 0) {
            List<Integer> ids = new ArrayList<>();
            templateDetails.forEach(item -> {
                ids.add(item.getKeyId());
            });
            return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.listByIds(ids)).buildQuerySuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Result.Builder<>().buildQuerySuccess(), HttpStatus.OK);
    }


    @PostMapping
    @Log("保存模板关键字信息")
    public ResponseEntity<Object> save(@RequestBody TemplateDetail detail) {
        return new ResponseEntity<>(new Result.Builder<>().setData(detail.insert()).buildSaveSuccess(), HttpStatus.OK);
    }


    @PutMapping
    @Log("更新模板关键字信息")
    public ResponseEntity<Object> update(@RequestBody TemplateDetail detail) {
        return new ResponseEntity<>(new Result.Builder<>().setData(templateDetailService.updateById(detail)).buildUpdateSuccess(), HttpStatus.OK);
    }

    @PostMapping("/all")
    @Log("获取所有关键字信息")
    public ResponseEntity<Object> saveAll(@RequestBody List<TemplateDetail> detailList) {
       boolean result =  templateDetailService.updateTemplateDetailBeforeSave(detailList);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildSaveSuccess(), HttpStatus.OK);
        }

    }

    @GetMapping("/wave/{tempId}")
    @Log("获取指定模板下的波形关键字列表")
    public ResponseEntity<Object> listWave(@PathVariable("tempId") Integer id) {
//        List<TemplateDetail> templateDetails = templateDetailService.listByTempIdAndType(id, 1);
//        if (templateDetails != null && templateDetails.size() > 0) {
//            List<Integer> ids = new ArrayList<>();
//            templateDetails.forEach(item -> {
//                ids.add(item.getKeyId());
//            });
//            List<DeviceKey> deviceKeys = deviceKeyService.listByIds(ids);
//            Collections.sort(deviceKeys);
//            return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeys).buildQuerySuccess(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new Result.Builder<>().buildQuerySuccess(), HttpStatus.OK);
        return new ResponseEntity<>(new Result.Builder<>().setData(templateDetailService.list(new QueryWrapper<TemplateDetail>()
                .eq("temp_id",id).eq("type",1))).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/data/{tempId}")
    @Log("获取指定模板下的数值关键字列表")
    public ResponseEntity<Object> listData(@PathVariable("tempId") Integer id) {
//        List<TemplateDetail> templateDetails = templateDetailService.listByTempIdAndType(id, 2);
//        if (templateDetails != null && templateDetails.size() > 0) {
//            List<Integer> ids = new ArrayList<>();
//            templateDetails.forEach(item -> {
//                ids.add(item.getKeyId());
//            });
//
//            List<DeviceKey> deviceKeys = deviceKeyService.listByIds(ids);
//            Collections.sort(deviceKeys);
//            return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeys).buildQuerySuccess(), HttpStatus.OK);
//        }
        return new ResponseEntity<>(new Result.Builder<>().setData(templateDetailService.list(new QueryWrapper<TemplateDetail>()
                .eq("temp_id",id).eq("type",2))).buildQuerySuccess(), HttpStatus.OK);
    }

}

