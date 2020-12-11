package com.allong.centerstation.controller;

import com.allong.centerstation.common.Result;
import com.allong.centerstation.common.enums.UserStatus;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Object> register(@RequestParam String username, @RequestParam String password) {
        User cacheUser = (User) userService.loadUserByUsername(username);
        if (cacheUser != null) {
            return new ResponseEntity<>(new Result.Builder<>().setData("用户名已存在").buildSaveFailed(), HttpStatus.OK);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setStatus(UserStatus.Enable.getCode());
        boolean result = userService.save(user);
        if (result) {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Result.Builder<>().buildSaveFailed(), HttpStatus.OK);
        }
    }


}
