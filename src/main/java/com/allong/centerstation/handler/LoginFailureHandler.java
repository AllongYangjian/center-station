package com.allong.centerstation.handler;

import com.alibaba.fastjson.JSONObject;
import com.allong.centerstation.domain.entity.Log;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.service.LogService;
import com.allong.centerstation.utils.StringUtils;
import com.allong.centerstation.utils.ThrowableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private LogService logService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, String[]> params = request.getParameterMap();
        Log log = new Log("ERROR", 0l);
        log.setDescription("登陆失败");
        log.setExceptionDetail(ThrowableUtil.getStackTrace(exception));
        log.setMethod("login");
        log.setParams(JSONObject.toJSONString(params));
        log.setRequestIp(StringUtils.getIpAddress(request));
        log.setUsername(JSONObject.toJSONString(params.get("username")[0]));
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setBrowser(StringUtils.getBrowser(request));
        logService.save(log);

        //登录失败信息返回
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", "500");
        paramMap.put("message", exception.getMessage());
        //设置返回请求头
        response.setContentType("application/json;charset=utf-8");
        //写出流
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(paramMap));
        out.flush();
        out.close();
    }

}