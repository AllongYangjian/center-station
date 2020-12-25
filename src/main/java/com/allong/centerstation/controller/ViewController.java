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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HOSPITAL')")
    @RequestMapping("/system/hospital")
    @Log("医院")
    public String systemHospital() {
        return "system/hospital";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEVICE')")
    @RequestMapping("/system/device")
    @Log("设备")
    public String systemDevice() {
        return "system/device";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ROLE')")
    @RequestMapping("/system/role")
    @Log("角色")
    public String systemRole() {
        return "system/role";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping("/system/user")
    @Log("账户")
    public String systemUser() {
        return "system/user";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_KEY')")
    @RequestMapping("/system/key")
    @Log("关键字")
    public String systemKey() {
        return "system/key";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BED')")
    @RequestMapping("/config/bed")
    @Log("床位")
    public String configBed() {
        return "config/bed";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEMPLATE')")
    @RequestMapping("/config/template")
    @Log("模板")
    public String configTemplate() {
        return "config/template";
    }

    @PreAuthorize("hasAnyRole('ROLE_BED')")
    @RequestMapping("/config/bind")
    @Log("绑定")
    public String configBind() {
        return "config/bind";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PATIENT')")
    @RequestMapping("/patient/import")
    @Log("病人信息")
    public String patientImport() {
        return "patient/import";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PATIENT')")
    @RequestMapping("/patient/history")
    @Log("病人记录")
    public String patientHistory() {
        return "patient/history";
    }

    @RequestMapping("/wave/data")
    @Log("波形数据")
    public String waveData() {
        return "wave/data";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERFORMANCE')")
    @RequestMapping("/operation/performance")
    @Log("性能")
    public String operationPerformance() {
        return "operation/performance";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LOG')")
    @RequestMapping("/operation/log")
    @Log("日志")
    public String operationLog() {
        return "operation/log";
    }

}
