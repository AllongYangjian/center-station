package com.allong.centerstation.controller;

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

    @RequestMapping("/system/hospital")
    public String systemHospital() {
        return "system/hospital";
    }

    @RequestMapping("/system/device")
    public String systemDevice() {
        return "system/device";
    }

    @RequestMapping("/system/role")
    public String systemRole() {
        return "system/role";
    }

    @RequestMapping("/system/user")
    public String systemUser() {
        return "system/user";
    }

    @RequestMapping("/system/key")
    public String systemKey() {
        return "system/key";
    }

    @RequestMapping("/config/patient")
    public String configPatient() {
        return "config/patient";
    }

     @RequestMapping("/config/template")
    public String configTemplate() {
        return "config/template";
    }


}
