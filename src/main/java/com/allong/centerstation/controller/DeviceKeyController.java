package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.allong.centerstation.service.DeviceKeyService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/key")
public class DeviceKeyController {
    @Autowired
    private DeviceKeyService deviceKeyService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Object> list(@PathVariable("deviceId") Integer deviceId) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.listByDeviceId(deviceId)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody DeviceKey deviceKey) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKey.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody DeviceKey deviceKey) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKey.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> listKeyDetail(){
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceKeyService.listKeyDetail()).buildQuerySuccess(), HttpStatus.OK);
    }
}

