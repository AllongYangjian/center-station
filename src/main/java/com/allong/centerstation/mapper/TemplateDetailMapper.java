package com.allong.centerstation.mapper;

import com.allong.centerstation.domain.entity.TemplateDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 模板详情 Mapper 接口
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface TemplateDetailMapper extends BaseMapper<TemplateDetail> {

    void updateAllEnable();
}
