package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.allong.centerstation.domain.entity.TemplateDetail;
import com.allong.centerstation.domain.entity.TemplateGroup;
import com.allong.centerstation.service.DeviceKeyService;
import com.allong.centerstation.service.TemplateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class TemplateDetailController {

    @Autowired
    private TemplateDetailService templateDetailService;

    @Autowired
    private DeviceKeyService deviceKeyService;

    @GetMapping("/{tempId}")
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
    public ResponseEntity<Object> save(@RequestBody TemplateDetail detail){
        return new ResponseEntity<>(new Result.Builder<>().setData(detail.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<Object> saveAll(@RequestBody List<TemplateDetail> detailList) {
        if(detailList!=null && detailList.size()>0){
            //删除旧的数据
            templateDetailService.deleteByTempId(detailList.get(0).getTempId());
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(templateDetailService.saveBatch(detailList)).buildSaveSuccess(), HttpStatus.OK);
    }

    @GetMapping("/wave/{tempId}")
    public ResponseEntity<Object> listWave(@PathVariable("tempId")Integer id){
        List<TemplateDetail> templateDetails = templateDetailService.listByTempIdAndType(id,1);
        if (templateDetails != null && templateDetails.size() > 0) {
            List<Integer> ids = new ArrayList<>();
            templateDetails.forEach(item -> {
                ids.add(item.getKeyId());
            });
            List<DeviceKey> deviceKeys = deviceKeyService.listByIds(ids);
            Collections.sort(deviceKeys);
            return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeys).buildQuerySuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Result.Builder<>().buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/data/{tempId}")
    public ResponseEntity<Object> listData(@PathVariable("tempId")Integer id){
        List<TemplateDetail> templateDetails = templateDetailService.listByTempIdAndType(id,2);
        if (templateDetails != null && templateDetails.size() > 0) {
            List<Integer> ids = new ArrayList<>();
            templateDetails.forEach(item -> {
                ids.add(item.getKeyId());
            });

            List<DeviceKey> deviceKeys = deviceKeyService.listByIds(ids);
            Collections.sort(deviceKeys);
            return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeys).buildQuerySuccess(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Result.Builder<>().buildQuerySuccess(), HttpStatus.OK);
    }

}

