package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.BedDetail;
import com.allong.centerstation.domain.entity.UserBed;
import com.allong.centerstation.mapper.UserBedMapper;
import com.allong.centerstation.service.UserBedService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
