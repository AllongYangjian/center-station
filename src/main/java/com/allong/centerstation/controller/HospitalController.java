package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Hospital;
import com.allong.centerstation.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 医院信息 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Hospital hospital) {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospital.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Hospital hospital) {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospital.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<Object> delete(@PathVariable("hospitalId") Integer hospitalId) {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.removeById(hospitalId)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(hospitalService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }

}

