package com.allong.centerstation.controller;

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
    public String index() {
        return "login";
    }


    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
