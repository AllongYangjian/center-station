package com.allong.centerstation.controller;

import com.allong.centerstation.logger.annotation.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: ViewController
 * Author:   YJ
 * Date:     2020/12/11 10:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/11 10:25        1.0              描述
 */
@Controller
@RequestMapping
public class ViewController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/system/hospital")
    @Log("医院")
    public String systemHospital() {
        return "system/hospital";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/system/device")
    @Log("设备")
    public String systemDevice() {
        return "system/device";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/system/role")
    @Log("角色")
    public String systemRole() {
        return "system/role";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/system/user")
    @Log("账户")
    public String systemUser() {
        return "system/user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/system/key")
    @Log("关键字")
    public String systemKey() {
        return "system/key";
    }

    @RequestMapping("/config/patient")
    @Log("病人")
    public String configPatient() {
        return "config/patient";
    }

    @RequestMapping("/config/template")
    @Log("模板")
    public String configTemplate() {
        return "config/template";
    }

    @RequestMapping("/wave/data")
    @Log("波形数据")
    public String waveData() {
        return "wave/data";
    }

}
