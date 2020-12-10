package com.allong.centerstation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: MyWebMvcConfig
 * Author:   YJ
 * Date:     2020/12/1 9:55
 * Description: 自定义mvc配置
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/1 9:55        1.0              描述
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/system/hospital").setViewName("/system/hospital");
        registry.addViewController("/system/device").setViewName("/system/device");
        registry.addViewController("/system/role").setViewName("/system/role");
        registry.addViewController("/system/user").setViewName("/system/user");
        registry.addViewController("/system/key").setViewName("/system/key");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
