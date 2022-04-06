package com.study.lecture.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.lecture.user.entity.LoginUser;
import com.study.lecture.user.entity.User;
import com.study.lecture.user.mapper.MenuMapper;
import com.study.lecture.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 实现UserDetailService，根据用id从数据库中查询用户信息.
 * </p>
 * <br>
 * Creation Time: 2022/4/5 19:08
 *
 * @author zqc
 * @since 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户表
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 权限表
     */
    @Autowired
    private MenuMapper menuMapper;

    /**
     * <p>通过数据库查询用户信息，封装成UserDetails传给Spring Security处理.</p>
     * <p>{@link com.study.lecture.user.entity.LoginUser}就是UserDetails的实现类。</p>
     * <br>
     *
     * @param username 用户登录账号
     * @return LoginUser对象
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        // 如果没有查询到用户则抛出异常,在过滤链中有异常捕获，这里抛出的异常会被捕获
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 查询对应的角色信息
        List<String> list = menuMapper.selectRoleByUserId(user.getId());
        // 把数据封装成UserDetails返回
        return new LoginUser(user, list);
    }
}
