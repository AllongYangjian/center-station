package com.allong.centerstation.controller;

import com.allong.centerstation.common.Result;
import com.allong.centerstation.common.enums.UserStatus;
import com.allong.centerstation.domain.UserDetailInfo;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: LoginController
 * Author:   YJ
 * Date:     2020/12/8 16:19
 * Description: 登录界面
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/8 16:19        1.0              描述
 */
@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @PostMapping("/register")
    @ResponseBody
    @Log("用户注册")
    public ResponseEntity<Object> register(@RequestBody UserDetailInfo userDetailInfo) {
        User cacheUser = (User) userService.loadUserByUsername(userDetailInfo.getUsername());
        if (cacheUser != null) {
            return new ResponseEntity<>(new Result.Builder<>().setData("用户名已存在").buildSaveFailed(), HttpStatus.OK);
        }
        User user = new User();
        user.setUsername(userDetailInfo.getUsername());
        user.setPassword(encoder.encode(userDetailInfo.getPassword()));
        user.setStatus(UserStatus.Enable.getCode());
        boolean result = userService.save(user);
        //保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDetailInfo.getUsername());
        userInfo.setName(userDetailInfo.getName());
        userInfo.setGender(userDetailInfo.getGender());
        userInfo.setAge(userDetailInfo.getAge());
        userInfo.setHid(userDetailInfo.getHid());
        userInfoService.save(userInfo);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveFailed(), HttpStatus.OK);
        }
    }


}
