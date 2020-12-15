package com.allong.centerstation.service;

import com.allong.centerstation.domain.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
public interface LogService extends IService<Log> {

    @Async
    void save(String username, String browser, String ipAddress, ProceedingJoinPoint joinPoint, Log log);
}
