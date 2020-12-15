package com.allong.centerstation.service.impl;

import cn.hutool.json.JSONObject;
import com.allong.centerstation.domain.entity.Log;
import com.allong.centerstation.mapper.LogMapper;
import com.allong.centerstation.service.LogService;
import com.allong.centerstation.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(String username, String browser, String ipAddress, ProceedingJoinPoint joinPoint, Log log) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        com.allong.centerstation.logger.annotation.Log  aopLog = method.getAnnotation(com.allong.centerstation.logger.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");

        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            try {
                JSONObject obj = new JSONObject(argValue);
                if(obj.containsKey("password")){
                    obj.set("password","******");
                }
                params.append(obj).append(" ");
            }catch (Exception e){
                params.append(argValue).append(" ");
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ipAddress);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                username = new JSONObject(argValues.get(0)).get("username").toString();
            } catch (Exception e) {
                LogServiceImpl.log.error(e.getMessage(), e);
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
//        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        baseMapper.insert(log);
    }
}
