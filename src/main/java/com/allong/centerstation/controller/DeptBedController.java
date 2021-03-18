package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.DeptBed;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.DeptBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2021-03-18
 */
@RestController
@RequestMapping("/api/deptBed")
public class DeptBedController {

    @Autowired
    private DeptBedService deptBedService;

    @GetMapping("/list/{deptId}")
    @Log("查询床位列表")
    public ResponseEntity<Object> list(@PathVariable("deptId") Integer deptId) {
        //如果是管理员，则显示全部床位
        return new ResponseEntity<>(new Result.Builder<>().setData(deptBedService.listByDeptId(deptId)).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Log("根据床位代码查询床位信息")
    public ResponseEntity<Object> query(@PathVariable("id") Integer id) {
        //如果是管理员，则显示全部床位
        return new ResponseEntity<>(new Result.Builder<>().setData(deptBedService.getById(id)).buildQuerySuccess(), HttpStatus.OK);
    }

    @PostMapping
    @Log("保存床位信息")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL','ROLE_PATIENT')")
    public ResponseEntity<Object> save(@RequestBody DeptBed bed) {

        DeptBed cache = deptBedService.getByDeptIdAndBed(bed.getDeptId(), bed.getBedNo());
        if (cache != null) {
            return new ResponseEntity<>(new Result.Builder<>().setMessage("该床位已存在").buildSaveFailed(), HttpStatus.OK);
        }
        boolean result = deptBedService.save(bed);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveFailed(), HttpStatus.OK);
        }
    }

    @PutMapping
    @Log("更新床位信息")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL','ROLE_PATIENT')")
    public ResponseEntity<Object> update(@RequestBody DeptBed bed) {
//        DeptBed cache = deptBedService.getByDeptIdAndBed(bed.getDeptId(), bed.getBedNo());
//        if (cache != null) {
//            cache.setBedLabel(bed.getBedLabel());
//            boolean result = deptBedService.updateById(bed);
//            if (result) {
//                return new ResponseEntity<>(new Result.Builder<>().buildUpdateSuccess(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new Result.Builder<>().buildUpdateFailed(), HttpStatus.OK);
//            }
//        }else {
//            boolean result = bed.insert();
//            if (result) {
//                return new ResponseEntity<>(new Result.Builder<>().buildUpdateSuccess(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new Result.Builder<>().buildUpdateFailed(), HttpStatus.OK);
//            }
//        }
        boolean result = bed.updateById();
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildUpdateSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildUpdateFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{deptId}")
    @Log("删除指定床位信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("deptId") Integer deptId) {
        boolean result = deptBedService.removeById(deptId);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteFailed(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/all/{deptId}")
    @Log("删除所有床位信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteAll(@PathVariable("deptId") Integer deptId) {
        boolean result = deptBedService.removeAllByDeptId(deptId);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildDeleteFailed(), HttpStatus.OK);
        }
    }

}

