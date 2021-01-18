package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.BedBindInfo;
import com.allong.centerstation.domain.entity.BedPatient;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.mapper.BedPatientMapper;
import com.allong.centerstation.service.BedPatientService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
@Service
public class BedPatientServiceImpl extends ServiceImpl<BedPatientMapper, BedPatient> implements BedPatientService {

    @Autowired
    private UserInfoService infoService;

    @Override
    public List<BedBindInfo> listBedBindInfoList(Integer hid) {
        if(hid ==null){
            List<Role> roles = SecurityUtils.getRoles();//如果是管理员
            boolean contain = false;
            if (roles != null && roles.size() > 0) {
                for (Role role : roles) {
                    if (role.getRoleName().toUpperCase().contains("ADMIN")) {
                        contain = true;
                        break;
                    }
                }
            }
            if(contain){
                return baseMapper.selectBedBindInfoList(null);
            }else {
                //获取用户信息
                UserInfo userInfo = infoService.loadUserByUserId(SecurityUtils.getCurrentUsername());
                return baseMapper.selectBedBindInfoList(userInfo.getHid());
            }
        }else {
            return baseMapper.selectBedBindInfoList(hid);
        }
    }

    @Transactional
    @Override
    public boolean saveBedBindInfo(BedPatient bedPatient) {
        //床位绑定信息
        BedPatient old = baseMapper.selectOne(new QueryWrapper<BedPatient>().eq("bed_id",bedPatient.getBedId()));
        if(old ==null){//说明改床还没有绑定病人
            return bedPatient.insert();
        }else {//如果改床绑定了病人
            if(bedPatient.getPatientId().equals(old.getPatientId())){//如果没有变更，则不需要操作
                return true;
            }else {//如果两次绑定的病人不一样，寻找该病人绑定的床位记录，将该床位记录中的病人设置成空
//                System.err.println(old.toString());

                BedPatient record = baseMapper.selectOne(new QueryWrapper<BedPatient>().eq("patient_id",bedPatient.getPatientId()));
                if(record!=null){
                    record.setPatientId(null);
                    baseMapper.updateById(record);
                }

                old.setPatientId(bedPatient.getPatientId());
                baseMapper.updateById(old);


                return true;
            }
        }
    }

    @Override
    public boolean updateBedBindInfo(BedPatient bedPatient) {
        return false;
    }
}
