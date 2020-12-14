package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.TemplateDetail;
import com.allong.centerstation.mapper.TemplateDetailMapper;
import com.allong.centerstation.service.TemplateDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 模板详情 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class TemplateDetailServiceImpl extends ServiceImpl<TemplateDetailMapper, TemplateDetail> implements TemplateDetailService {

    @Override
    public List<TemplateDetail> listByTempId(Integer tempId) {
        return baseMapper.selectList(new QueryWrapper<TemplateDetail>().eq("temp_id",tempId));
    }

    @Override
    public void deleteByTempId(Integer tempId) {
        baseMapper.delete(new QueryWrapper<TemplateDetail>().eq("temp_id",tempId));
    }

    @Override
    public List<TemplateDetail> listByTempIdAndType(Integer id, int type) {
        return baseMapper.selectList(new QueryWrapper<TemplateDetail>().eq("temp_id",id).eq("type",type));
    }

}
