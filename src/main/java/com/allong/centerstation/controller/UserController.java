package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.User;
import com.allong.centerstation.service.UserService;
import com.allong.centerstation.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<Object> queryUserByUsername(@PathVariable("username") String username) {
        User user = (User) userService.loadUserByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(user).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/fetchCurrent")
    public ResponseEntity<Object> queryCurrentUser(){
        String username = SecurityUtils.getCurrentUsername();
        User user = (User) userService.loadUserByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(user).buildQuerySuccess(), HttpStatus.OK);
    }

}

