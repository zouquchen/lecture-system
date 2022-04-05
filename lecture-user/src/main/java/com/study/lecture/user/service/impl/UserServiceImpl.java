package com.study.lecture.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.utils.JwtUtil;
import com.study.lecture.common.utils.R;
import com.study.lecture.user.entity.LoginUser;
import com.study.lecture.user.entity.User;
import com.study.lecture.user.mapper.UserMapper;
import com.study.lecture.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录服务
     * @param user 用户
     * @return 响应类
     */
    @Override
    public R login(User user) {
        // 认证的时候需要Authentication对象，所以需要一个Authentication的实现类，这里选择了UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        // AuthenticationManager authenticate方法进行认证。在SecurityConfig配置类中，我们将AuthenticationManager注入到容器中。
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果认证通过，authenticate里将包含principal属性，该属性的值就是LoginUser，
        // 如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJwt(id);

        // 把完整的用户信息存入redis，userid作为key
        redisTemplate.opsForValue().set("login:" + id, loginUser);

        // 返回响应类给前端
        return R.ok("登录成功").put("token", jwt);
    }

    /**
     * 登出服务
     * @return
     */
    @Override
    public R logout() {
        // 获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();

        // 删除redis当中的值
        redisTemplate.delete("login:" + id);

        return R.ok("注销成功");
    }

}
