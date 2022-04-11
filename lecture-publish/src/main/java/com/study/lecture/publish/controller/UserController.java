package com.study.lecture.publish.controller;

import com.study.lecture.common.entity.User;
import com.study.lecture.common.service.UserService;
import com.study.lecture.common.utils.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户控制器
 * </p>
 * <p>CrossOrigin 解决跨域问题</p>
 * <br>
 * Creation Time: 2022/4/11 12:35
 *
 * @author zqc
 * @since 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @DubboReference(version = "1.0")
    private UserService userService;

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
