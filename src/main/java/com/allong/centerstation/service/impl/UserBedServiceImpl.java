package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.BedDetail;
import com.allong.centerstation.domain.PatientDetail;
import com.allong.centerstation.domain.entity.BedPatient;
import com.allong.centerstation.domain.entity.Patient;
import com.allong.centerstation.domain.entity.UserBed;
import com.allong.centerstation.mapper.UserBedMapper;
import com.allong.centerstation.service.BedPatientService;
import com.allong.centerstation.service.PatientService;
import com.allong.centerstation.service.UserBedService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户床位信息 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-25
 */
@Service
public class UserBedServiceImpl extends ServiceImpl<UserBedMapper, UserBed> implements UserBedService {

    @Autowired
    private BedPatientService bedPatientService;

    @Autowired
    private PatientService patientService;

    @Override
    public boolean saveUserBedList(List<BedDetail> beds) {
        String username = SecurityUtils.getCurrentUsername();
        baseMapper.delete(new UpdateWrapper<UserBed>().eq("username",username));
        List<UserBed> bedList = new ArrayList<>();
        for(BedDetail item:beds){
            if(item.isSelected()){
                UserBed userBed = new UserBed();
                userBed.setUsername(username);
                userBed.setBedId(item.getId());
                bedList.add(userBed);
            }
        }
        return saveBatch(bedList);
    }

    @Override
    public List<PatientDetail> queryUserBedPatientList() {
        List<UserBed> beds  = baseMapper.selectList(new QueryWrapper<UserBed>().eq("username",SecurityUtils.getCurrentUsername()));
        List<Integer> bedIds = new ArrayList<>();//获取床位列表id
        beds.forEach(item->{
            bedIds.add(item.getBedId());
        });

        List<BedPatient> bedPatients = bedPatientService.list(new QueryWrapper<BedPatient>().in("bed_id",bedIds));
        List<Integer> pidList = new ArrayList<>();
        bedPatients.forEach(item->{
            pidList.add(item.getPatientId());
        });

        return patientService.listDetailByIds(pidList);
    }
}
