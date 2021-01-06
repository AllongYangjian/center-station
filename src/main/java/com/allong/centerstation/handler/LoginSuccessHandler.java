package com.allong.centerstation.handler;

import com.alibaba.fastjson.JSONObject;
import com.allong.centerstation.domain.entity.Log;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.service.LogService;
import com.allong.centerstation.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: LoginSuccessHandler
 * Author:   YJ
 * Date:     2020/12/9 14:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/9 14:32        1.0              描述
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private LogService logService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        User user = (User) authentication.getPrincipal();
        user.setPassword(null);
        Log log = new Log("INFO", 0l);
        log.setDescription("登录成功");
        log.setMethod("login");
        log.setParams(JSONObject.toJSONString(user));
        log.setRequestIp(StringUtils.getIpAddress(request));
        log.setUsername(user.getUsername());
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setBrowser(StringUtils.getBrowser(request));
        logService.save(log);

        //登录成功返回
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", "200");
        paramMap.put("message", "登录成功!");
        paramMap.put("session",request.getSession().getId());
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        //写出流
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(paramMap));
        out.flush();
        out.close();
    }
}
