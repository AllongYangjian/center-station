package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.BedThresholdDetail;
import com.allong.centerstation.domain.entity.BedThreshold;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.mapper.BedThresholdMapper;
import com.allong.centerstation.service.BedThresholdService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 床位报警阀值设置 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-28
 */
@Service
public class BedThresholdServiceImpl extends ServiceImpl<BedThresholdMapper, BedThreshold> implements BedThresholdService {

    @Autowired
    private UserInfoService infoService;

    @Override
    public List<BedThresholdDetail> listDetails() {
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
            return baseMapper.listDetails(null);
        }else {
            UserInfo userInfo = infoService.loadUserByUserId(SecurityUtils.getCurrentUsername());
            return baseMapper.listDetails(userInfo.getHid());
        }

    }
}
