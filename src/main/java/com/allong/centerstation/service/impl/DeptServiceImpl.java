package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.Dept;
import com.allong.centerstation.mapper.DeptMapper;
import com.allong.centerstation.service.DeptBedService;
import com.allong.centerstation.service.DeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptBedService deptBedService;

    @Override
    public List<Dept> listByHid(Integer hid) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("hospital_id", hid);
        return list(wrapper);
    }

    @Override
    public Dept getByDeptCode(String deptCode) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_code", deptCode);
        return getOne(wrapper);
    }

    @Override
    public boolean removeAllByHid(Integer hid) {
        UpdateWrapper<Dept> wrapper = new UpdateWrapper<>();
        wrapper.eq("hospital_id", hid);
        return remove(wrapper);
    }

    @Override
    public boolean cascadeDelete(Integer deptId) {
        deptBedService.removeAllByDeptId(deptId);
        return removeById(deptId);
    }
}
