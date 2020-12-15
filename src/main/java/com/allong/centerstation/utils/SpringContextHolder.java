package com.allong.centerstation.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: SpringContextHolder
 * Author:   YJ
 * Date:     2020/11/11 16:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 16:34        1.0              描述
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    /**
     * 缓存项目的 ApplicationContext
     */
    private static ApplicationContext applicationContext = null;

    private static final List<CallBack> CALL_BACKS = new ArrayList<>();
    /**
     * 是否添加回调
     */
    private static boolean addCallback = true;

    public synchronized static void addCallBacks(CallBack callBack){
        if(addCallback){
            SpringContextHolder.CALL_BACKS.add(callBack);
        }else {
            log.warn("CallBack：{} 已无法添加！立即执行", callBack.getCallBackName());
            callBack.executor();
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName){
        assertContextInjected();
        return (T)applicationContext.getBean(beanName);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取SpringBoot 配置信息
     * @param property 属性值
     * @param defaultValue 默认值
     * @param requireType 类型
     * @param <T> T
     * @return T
     */
    public static <T> T getProperties(String property,T defaultValue,Class<T> requireType){
        T result = defaultValue;
        try {
            result = getBean(Environment.class).getProperty(property,requireType);
        }catch (Exception ignored){}
        return result;
    }

    /**
     * 获取String 类型的配置信息
     * @param property 属性值
     * @return String
     */
    public static String getProperties(String property){
        return getProperties(property,null,String.class);
    }

    /**
     * 获取 SpringBoot 配置文件的配置信息，如果没有获取到，则默认为空
     * @param property
     * @param requireType
     * @param <T>
     * @return
     */
    public static <T> T getProperty(String property,Class<T> requireType){
        return getProperties(property,null,requireType);
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    private static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext:"
                + applicationContext);
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext;
        if(addCallback){
            for(CallBack callBack:CALL_BACKS){
                callBack.executor();
            }
            CALL_BACKS.clear();
        }
        SpringContextHolder.addCallback = false;//初始化完成后不在添加回调
    }


    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext属性未注入, 请在applicationContext" +
                    ".xml中定义SpringContextHolder或在SpringBoot启动类中注册SpringContextHolder.");
        }
    }
}
