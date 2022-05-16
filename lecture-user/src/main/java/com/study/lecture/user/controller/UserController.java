package com.study.lecture.user.controller;


import com.study.lecture.common.utils.R;
import com.study.lecture.common.entity.user.User;
import com.study.lecture.common.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录，前端nginx转发到这里实现登录
     * @param user 请求域中的用户
     * @return
     */
    @RequestMapping("login")
    public R login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 获取用户基本信息：姓名、角色、头像url
     * 前端的每一次请求都会先获取用户的基本信息
     * @return
     */
    @GetMapping("info")
    public R info() {
        System.out.println("收到info请求！");
        return userService.info();
    }

    /**
     * 获取当前已登录用户信息
     * @return 当前用户信息
     */
    @GetMapping("getLoginUser")
    public R getLoginUser() {
        return userService.getLoginUser();
    }
    /**
     * 用户登出，前端nginx转发到这里实现登出
     * @return
     */
    @RequestMapping("logout")
    public R logout() {
        return userService.logout();
    }

    /**
     * controller测试
     * 当前登录用户必须带有 test 权限才能访问
     * @return
     */
    @RequestMapping("hello")
    @PreAuthorize("hasAuthority('admin')")
    public R hello() {
        return R.ok("hello");
    }
}