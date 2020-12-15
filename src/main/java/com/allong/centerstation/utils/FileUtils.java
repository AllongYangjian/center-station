package com.allong.centerstation.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: FileUtils
 * Author:   YJ
 * Date:     2020/11/11 17:01
 * Description: 文件工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/11/11 17:01        1.0              描述
 */
public class FileUtils extends cn.hutool.core.io.FileUtil {

    /**
     * 系统临时目录
     * <br>
     * windows 包含路径分割符，但Linux 不包含,
     * 在windows \\==\ 前提下，
     * 为安全起见 同意拼装 路径分割符，
     * <pre>
     *       java.io.tmpdir
     *       windows : C:\Users/xxx\AppData\Local\Temp\
     *       linux: /temp
     * </pre>
     */
    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;

    public static File inputStreamToFile(InputStream ins, String name) throws Exception{
        File file = new File(SYS_TEM_DIR + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }
}
