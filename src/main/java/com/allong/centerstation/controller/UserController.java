package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.entity.User;
import com.allong.centerstation.domain.entity.UserInfo;
import com.allong.centerstation.logger.annotation.Log;
import com.allong.centerstation.service.RoleService;
import com.allong.centerstation.service.UserInfoService;
import com.allong.centerstation.service.UserService;
import com.allong.centerstation.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final RoleService roleService;

    private final UserInfoService userInfoService;

    @GetMapping("/{username}")
    @Log("获取用户信息")
    public ResponseEntity<Object> queryUserByUsername(@PathVariable("username") String username) {
        User user = (User) userService.loadUserByUsername(username);
        if (user != null) {
            user.setPassword(null);
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(user).buildQuerySuccess(), HttpStatus.OK);
    }

    @GetMapping("/fetchCurrent")
    @Log("获取当前用户信息")
    public ResponseEntity<Object> queryCurrentUser() {
        String username = SecurityUtils.getCurrentUsername();
        UserInfo user = userInfoService.loadUserByUserId(username);
        return new ResponseEntity<>(new Result.Builder<>().setData(user).buildQuerySuccess(), HttpStatus.OK);
    }


    @GetMapping
    @Log("获取账户列表")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Object> list() {
        List<User> list = userService.list();
        if (list != null) {
            list.forEach(item -> item.setPassword(null));
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(list).buildQuerySuccess(), HttpStatus.OK);
    }

    @PutMapping
    @Log("更新账户信息")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Object> update(@RequestBody User user) {
        return new ResponseEntity<>(new Result.Builder<>().setData(user.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @GetMapping("/role/{uid}")
    @Log("获取用户角色列表")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Object> list(@PathVariable("uid") Integer uid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(roleService.selectAllByUserId(uid)).buildQuerySuccess(), HttpStatus.OK);
    }

}

