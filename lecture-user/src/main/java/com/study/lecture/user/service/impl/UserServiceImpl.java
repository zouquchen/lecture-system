package com.study.lecture.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.utils.JwtUtil;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.entity.user.User;
import com.study.lecture.user.mapper.UserMapper;
import com.study.lecture.common.service.user.UserService;
import io.jsonwebtoken.Claims;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@DubboService(version = "1.0")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

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
        // 设置过期时间为1天
        redisTemplate.opsForValue().set("login:" + id, loginUser, 1, TimeUnit.DAYS);

        // 返回响应类给前端
        return R.ok("登录成功").put("token", jwt);
    }

    /**
     * 获取当前登录用户基本信息
     * @return 响应信息
     */
    @Override
    public R info() {
        // 因为用户已经登录，可以从SecurityContextHolder获取该用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return R.ok()
                .put("name", loginUser.getUsername())
                .put("roles", loginUser.getPermissions())
                .put("avatar", loginUser.getUser().getAvatar());
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

    /**
     * 根据用户id，从redis内获取用户信息
     * @param key 用户key (格式为 login:用户id）
     * @return 用户信息
     */
    @Override
    public LoginUser getUserFromRedisById(String key) {
        return (LoginUser) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    @Override
    public R getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return R.ok().put("authentication", authentication);
    }


}
