package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 杨建
 * @since 2021-03-18
 */
public interface DeptService extends IService<Dept> {
    /**
     * 根据医院id查询科室列表
     *
     * @param hid
     * @return
     */
    List<Dept> listByHid(Integer hid);

    Dept getByDeptCode(String deptCode);

    boolean removeAllByHid(Integer hid);

    boolean cascadeDelete(Integer deptId);
}
