package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.TemplateGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 模板名称 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface TemplateGroupService extends IService<TemplateGroup> {

    List<TemplateGroup> listByUsername(String username);

    void updateAllDisable();

    boolean updateOneEnable(Integer tempId);
}
