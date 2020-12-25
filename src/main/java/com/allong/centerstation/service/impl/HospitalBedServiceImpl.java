package com.allong.centerstation.service.impl;

import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.BedDetail;
import com.allong.centerstation.domain.entity.HospitalBed;
import com.allong.centerstation.domain.entity.Role;
import com.allong.centerstation.domain.entity.UserBed;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.mapper.HospitalBedMapper;
import com.allong.centerstation.mapper.UserInfoMapper;
import com.allong.centerstation.service.HospitalBedService;
import com.allong.centerstation.service.UserBedService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 医院床位列表 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-23
 */
@Service
public class HospitalBedServiceImpl extends ServiceImpl<HospitalBedMapper, HospitalBed> implements HospitalBedService {

    @Autowired
    private UserInfoService infoService;

    @Autowired
    private UserBedService userBedService;

    @Override
    public List<BedDetail> listBedsWithSelected() {
        //如果是管理员，则显示全部医院
        List<BedDetail> bedDetails ;
        String username = SecurityUtils.getCurrentUsername();
        if (username == null || username.equals("")) {//表示未登录，注解界面获取
            return  null;
        } else {
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
            if (contain) {
                bedDetails = baseMapper.listBedDetailByHid(null);
            } else {
                //获取用户信息
                UserInfo userInfo = infoService.loadUserByUserId(username);
                bedDetails = baseMapper.listBedDetailByHid(userInfo.getHid());
            }
            //判断床位是否已选择
            List<UserBed> userBeds =userBedService.list(new QueryWrapper<UserBed>().eq("username",username));
            for(BedDetail item:bedDetails){
                for(UserBed bed:userBeds){
                    if(item.getId().equals(bed.getBedId())){
                        item.setSelected(true);
                        break;
                    }
                }
            }
        }

        return bedDetails;
    }
}
