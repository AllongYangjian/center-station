package com.allong.centerstation;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: PasswordEncoderTest
 * Author:   YJ
 * Date:     2020/12/9 11:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/9 11:36        1.0              描述
 */
public class PasswordEncoderTest {

    @Test
    public void testBCryptPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.err.println(encoder.encode("123456"));
    }
}
