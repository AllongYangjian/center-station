package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.Dept;
import com.allong.centerstation.domain.entity.DeptBed;
import com.allong.centerstation.mapper.DeptBedMapper;
import com.allong.centerstation.service.DeptBedService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.nio.file.Watchable;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2021-03-18
 */
@Service
public class DeptBedServiceImpl extends ServiceImpl<DeptBedMapper, DeptBed> implements DeptBedService {

    @Override
    public List<DeptBed> listByDeptId(Integer deptId) {
        QueryWrapper<DeptBed> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id", deptId);
        return list(wrapper);
    }

    @Override
    public DeptBed getByDeptIdAndBed(Integer deptId, String bedNo) {
        QueryWrapper<DeptBed> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id", deptId).eq("bed_no",bedNo);
        return getOne(wrapper);
    }

    @Override
    public boolean removeAllByDeptId(Integer deptId) {
        UpdateWrapper<DeptBed> wrapper = new UpdateWrapper<>();
        wrapper.eq("dept_id",deptId);
        return remove(wrapper);
    }
}
