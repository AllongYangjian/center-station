package com.allong.centerstation.controller;

import com.allong.centerstation.logger.annotation.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: IndexController
 * Author:   YJ
 * Date:     2020/12/2 14:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/2 14:00        1.0              描述
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @Log("打开网站")
    public String index() {
        return "login";
    }

    @RequestMapping("/home")
    @Log("访问主页")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    @Log("登录页面")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register")
    @Log("注册页面")
    public String registerPage() {
        return "register";
    }
}
