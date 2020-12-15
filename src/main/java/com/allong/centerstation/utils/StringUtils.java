package com.allong.centerstation.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.allong.centerstation.common.CommonConstant;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: StringUtils
 * Author:   YJ
 * Date:     2020/11/11 16:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 16:26        1.0              描述
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final Logger log = LoggerFactory.getLogger(StringUtils.class);
    /**
     * 是否采用本地ip地址数据库区判断 ip
     */
    private static boolean ipLocal = false;
    /**
     * ip2region 数据库解析类
     */
    private static DbConfig config;
    /**
     * 数据库文件存放地址
     */
    private static File file = null;

    private static final char SEPARATOR = '_';

    private static final String UNKNOWN = "unknown";

    static {
        SpringContextHolder.addCallBacks(()->{
            StringUtils.ipLocal =
                    SpringContextHolder.getProperties("ip.local-parsing",false,Boolean.class);
            if(ipLocal){
                String path = "ip2region/ip2region.db";
                String name = "ip2region.db";

                try {
                    config = new DbConfig();
                    file = FileUtils.inputStreamToFile(new ClassPathResource(path).getInputStream(),name);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 从请求头中获取浏览器信息
     * @param request HttpServletRequest
     * @return
     */
    public static String getBrowser(HttpServletRequest request){
        UserAgent agent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = agent.getBrowser();
        return browser.getName();
    }

    /**
     * 从请求头中获取ip地址信息
     * @param request HttpServletRequest
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        //请求头中获取ip
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }

        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    public static String getCityInfo(String ip){
        if(ipLocal){
            return getLocalCityInfo(ip);
        }else {
            return getHttpCityInfo(ip);
        }
    }

    /**
     * 根据ip获取详细地址
     */
    private static String getHttpCityInfo(String ip) {
        String api = String.format(CommonConstant.Url.IP_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }

    private static String getLocalCityInfo(String ip) {
        try {
            DataBlock dataBlock = new DbSearcher(config, file.getPath())
                    .binarySearch(ip);
            String region = dataBlock.getRegion();
            String address = region.replace("0|", "");
            char symbol = '|';
            if (address.charAt(address.length() - 1) == symbol) {
                address = address.substring(0, address.length() - 1);
            }
            return address.equals(CommonConstant.REGION) ? "内网IP" : address;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
}
