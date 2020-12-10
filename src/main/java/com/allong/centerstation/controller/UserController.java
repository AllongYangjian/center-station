package com.allong.centerstation.controller;


import com.allong.centerstation.common.Result;
import com.allong.centerstation.domain.Role;
import com.allong.centerstation.domain.User;
import com.allong.centerstation.service.RoleService;
import com.allong.centerstation.service.UserService;
import com.allong.centerstation.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @GetMapping
    public ResponseEntity<Object> list() {
        List<User> list = userService.list();
        if(list!=null){
            list.forEach(item->item.setPassword(null));
        }
        return new ResponseEntity<>(new Result.Builder<>().setData(list).buildQuerySuccess(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody User user) {
        return new ResponseEntity<>(new Result.Builder<>().setData(user.updateById()).buildUpdateSuccess(), HttpStatus.OK);
    }

    @GetMapping("/role/{uid}")
    public ResponseEntity<Object> list(@PathVariable("uid")Integer uid) {
        return new ResponseEntity<>(new Result.Builder<>().setData(roleService.selectAllByUserId(uid)).buildQuerySuccess(), HttpStatus.OK);
    }

}

