package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.DeptBed;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨建
 * @since 2021-03-18
 */
public interface DeptBedService extends IService<DeptBed> {

    List<DeptBed> listByDeptId(Integer deptId);

    DeptBed getByDeptIdAndBed(Integer deptId, String bedNo);

    boolean removeAllByDeptId(Integer deptId);
}
