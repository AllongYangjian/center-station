package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.BedBindInfo;
import com.allong.centerstation.domain.entity.BedPatient;
import com.allong.centerstation.domain.entity.DeviceKey;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.BedPatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/api/bedPatient")
public class BedPatientController {

    @Autowired
    private BedPatientService patientService;

    @GetMapping
    @Log("查询所有床位绑定列表")
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listBedBindInfoList(null)).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{hid}")
    @Log("查询指定医院床位绑定列表")
    public ResponseEntity<Object> list(@PathVariable("hid") Integer hid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.listBedBindInfoList(hid)).buildQuerySuccess(), HttpStatus.OK);
    }


    @PostMapping
    @Log("保存床位绑定信息")
    public ResponseEntity<Object> save(@RequestBody BedPatient bedPatient) {
        boolean result = patientService.saveBedBindInfo(bedPatient);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildSaveSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildSaveFailed(), HttpStatus.OK);
        }

    }

    @PutMapping
    @Log("更新床位绑定信息")
    public ResponseEntity<Object> update(@RequestBody BedPatient bedPatient) {
        boolean result = patientService.saveBedBindInfo(bedPatient);
        if(result){
            return new ResponseEntity<>(new Result.Builder<>().setData(true).buildUpdateSuccess(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Result.Builder<>().setData(false).buildUpdateFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除床位绑定信息")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new Result.Builder<>().setData(patientService.removeById(id)).buildDeleteSuccess(), HttpStatus.OK);
    }
}

