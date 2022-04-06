package com.study.lecture.user.controller;


import com.study.lecture.common.utils.R;
import com.study.lecture.user.entity.User;
import com.study.lecture.user.mapper.UserMapper;
import com.study.lecture.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 测试登录
     * @param user 请求域中的用户
     * @return
     */
    @RequestMapping("login")
    public R login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 测试登出
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
