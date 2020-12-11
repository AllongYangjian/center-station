package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.DeviceType;
import com.allong.centerstation.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 设备类型 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/device")
public class DeviceTypeController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceTypeService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody DeviceType deviceType) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceType.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody DeviceType deviceType) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceType.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{did}")
    public ResponseEntity<Object> delete(@PathVariable("did") Integer did) {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceTypeService.removeById(did)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(deviceTypeService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

