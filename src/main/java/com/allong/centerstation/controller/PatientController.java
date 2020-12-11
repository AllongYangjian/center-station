package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.Patient;
import com.allong.centerstation.service.PatientService;
import com.allong.centerstation.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 病人信息表 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.list()).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{hid}")
    public ResponseEntity<Object> list(@PathVariable("hid") Integer hid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listByHid(hid)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Patient patient) {
        patient.setRecordTime(new Date());
        patient.setRecordUser(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(new Result.Builder<>().setData(patient.insert()).buildSaveSuccess(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Patient patient) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patient.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.remove(null)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

