package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.DeviceKeyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 设备关键字 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_KEY','ROLE_TEMPLATE')")
@RequestMapping("/api/key")
public class DeviceKeyController {
    @Autowired
    private DeviceKeyService deviceKeyService;

    @GetMapping
    @Log("查询设备关键字列表")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{deviceId}")
    @Log("查询指定设备的关键字列表")
    public ResponseEntity<Object> list(@PathVariable("deviceId") Integer deviceId) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.listByDeviceId(deviceId)).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/code")
    @Log("根据代码插叙设备关键字")
    public ResponseEntity<Object> list(@RequestParam("code") String code) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.list(new QueryWrapper<DeviceKey>().like("code",  code))).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存设备关键字")
    public ResponseEntity<Object> save(@RequestBody DeviceKey deviceKey) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKey.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    @Log("更新设备关键字")
    public ResponseEntity<Object> update(@RequestBody DeviceKey deviceKey) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKey.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Log("删除指定设备关键字")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    @Log("删除所有设备关键字")
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @GetMapping("/detail")
    @Log("获取设备关键字详情")
    public ResponseEntity<Object> listKeyDetail() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.listKeyDetail()).buildQuerySuccess(), HttpStatus.OK);
    }
}

