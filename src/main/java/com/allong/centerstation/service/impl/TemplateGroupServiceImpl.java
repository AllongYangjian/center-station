package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.TemplateGroup;
import com.allong.centerstation.mapper.TemplateGroupMapper;
import com.allong.centerstation.service.TemplateGroupService;
import com.allong.centerstation.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 模板名称 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class TemplateGroupServiceImpl extends ServiceImpl<TemplateGroupMapper, TemplateGroup> implements TemplateGroupService {

    @Override
    public List<TemplateGroup> listByUsername(String username) {
        return baseMapper.selectList(new QueryWrapper<TemplateGroup>().eq("username",username).or().eq("username","0"));
    }


    @Override
    public void updateAllDisable() {
        baseMapper.updateAllDisable(SecurityUtils.getCurrentUsername());
    }

    @Override
    public boolean updateOneEnable(Integer tempId) {
        return  baseMapper.updateOneEnable(tempId);
    }
}
