package com.allong.centerstation.logger.aspect;

import com.allong.centerstation.domain.entity.Log;
import com.allong.centerstation.service.LogService;
import com.allong.centerstation.utils.RequestHolder;
import com.allong.centerstation.utils.SecurityUtils;
import com.allong.centerstation.utils.StringUtils;
import com.allong.centerstation.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: LogAspect
 * Author:   YJ
 * Date:     2020/11/11 16:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 16:04        1.0              描述
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final LogService loggerService;

    private ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LogService loggerService) {
        this.loggerService = loggerService;
    }

    @Pointcut("@annotation(com.allong.centerstation.logger.annotation.Log)")
    public void logPointcut(){

    }

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        //获取请求的路径，参数，地址，ip等信息
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        loggerService.save(getUsername(), StringUtils.getBrowser(request),StringUtils.getIpAddress(request),joinPoint,log);
        return result;
    }

    @AfterThrowing(pointcut = "logPointcut()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint,Throwable e){
        Log log  = new Log("ERROR",System.currentTimeMillis()-currentTime.get());
        currentTime.remove();

        log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        loggerService.save(getUsername(),StringUtils.getBrowser(request),StringUtils.getIpAddress(request),(ProceedingJoinPoint)joinPoint,log);
    }

    private String getUsername() {
        try {
            return SecurityUtils.getCurrentUsername();
        }catch (Exception e){
//            e.printStackTrace();
            return "";
        }
    }
}
