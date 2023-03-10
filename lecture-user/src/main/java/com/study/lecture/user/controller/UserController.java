package com.study.lecture.user.controller;


import com.study.lecture.common.utils.R;
import com.study.lecture.common.entity.user.User;
import com.study.lecture.common.service.user.UserService;
import com.study.lecture.common.vo.PasswordVo;
import com.study.lecture.common.vo.UserListQueryVo;
import com.study.lecture.common.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    @PostMapping("login")
    public R login(@RequestBody User user) {
        log.debug("UserController : login");
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
     * 用户登出，前端nginx转发到这里实现登出
     * @return
     */
    @PostMapping("logout")
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

    @GetMapping("getRoleList")
    public R getRoleList() {
        return userService.getRoleList();
    }

    /**
     * 用户列表
     * @param page 当前页
     * @param limit 当前页显示数量
     * @param userListQueryVo 查询条件
     * @return 相响应类
     */
    @PostMapping("getUserList/{page}/{limit}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public R getUserList(@PathVariable int page, @PathVariable int limit,
                         @RequestBody(required = false) UserListQueryVo userListQueryVo) {
        return userService.getUserPageList(page, limit, userListQueryVo);
    }

    /**
     * 获取用户详细信息
     * @return 用户信息
     */
    @GetMapping("getUserInfo")
    public R getUserInfo() {
        User user = userService.getUserInfo();
        return R.ok().put("userInfo", user);
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 成功标志
     */
    @PostMapping("updateUserInfo")
    @PreAuthorize("hasAnyAuthority('admin', 'manager', 'student')")
    public R updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return R.ok();
    }

    /**
     * 修改密码
     * @param passwordVo 密码VO
     * @return 成功标志
     */
    @PostMapping("updatePassword")
    @PreAuthorize("hasAnyAuthority('admin', 'manager', 'student')")
    public R updatePassword(@RequestBody PasswordVo passwordVo) {
        userService.updatePassword(passwordVo);
        return R.ok();
    }

    /**
     * 添加新用户
     * @param userVo 新用户信息
     * @return
     */
    @PostMapping("addUser")
    @PreAuthorize("hasAuthority('admin')")
    public R addUser(@RequestBody UserVo userVo) {
        userService.addUser(userVo);
        return R.ok();
    }

    /**
     * 逻辑删除用户
     * @param id 用户id
     * @return
     */
    @PostMapping("deleteUser/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public R deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return R.ok();
    }
}
