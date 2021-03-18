package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.BedCriteria;
import com.allong.centerstation.domain.entity.Dept;
import com.allong.centerstation.domain.entity.DeptBed;
import com.allong.centerstation.mapper.DeptBedMapper;
import com.allong.centerstation.service.DeptBedService;
import com.allong.centerstation.service.DeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        wrapper.eq("dept_id", deptId).eq("bed_no", bedNo);
        return getOne(wrapper);
    }

    @Override
    public boolean removeAllByDeptId(Integer deptId) {
        UpdateWrapper<DeptBed> wrapper = new UpdateWrapper<>();
        wrapper.eq("dept_id", deptId);
        return remove(wrapper);
    }

    @Override
    public boolean saveListByDeptId(Integer hid, Integer deptId, BedCriteria criteria) {
        removeAllByDeptId(deptId);
        List<DeptBed> bedList = new ArrayList<>();
        for (int x = criteria.getStart(); x <= criteria.getEnd(); x++) {
            String[] filter = criteria.getFilter().split(",");
            boolean contain = false;
            for (String item : filter) {
                if (String.valueOf(x).equals(item)) {
                    contain = true;
                    break;
                }
            }
            if (contain) {
                continue;
            }
            DeptBed bed = new DeptBed();
            bed.setHospitalId(hid);
            bed.setDeptId(deptId);
            bed.setBedNo(String.valueOf(x));
            String formatStr = "%s%0" + criteria.getLength() + "d%s";
            String prefix = criteria.getPrefix() == null ? "" : criteria.getPrefix();
            String suffix = criteria.getSuffix() == null ? "" : criteria.getSuffix();
            bed.setBedLabel(String.format(Locale.getDefault(), formatStr, prefix, x, suffix));

            bedList.add(bed);
        }
        return saveBatch(bedList);
    }
}
