package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.TemplateDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 模板详情 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface TemplateDetailService extends IService<TemplateDetail> {

    List<TemplateDetail> listByTempId(Integer tempId);

    void deleteByTempId(Integer tempId);

    List<TemplateDetail> listByTempIdAndType(Integer id, int type);
}
