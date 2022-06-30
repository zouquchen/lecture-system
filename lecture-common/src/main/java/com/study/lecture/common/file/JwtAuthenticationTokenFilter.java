package com.study.lecture.common.file;

import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.user.UserService;
import com.study.lecture.common.utils.JwtUtil;
import com.study.lecture.common.utils.ResultCodeEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 权限认证过滤器
 * 每个请求都要经过这个过滤器
 * </p>
 * <p>1 获取请求头中的token，对token进行解析取出其中的userid。 </p>
 * <p>2 使用userid去redis中获取对应的LoginUser对象。 </p>
 * <p>3 封装Authentication对象存入SecurityContextHolder </p>
 * <br>
 * Creation Time: 2022/4/5 20:06
 *
 * @author zqc
 * @since 1.0
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @DubboReference(version = "1.0")
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("经过JwtAuthenticationTokenFilter！");
        log.debug("经过JwtAuthenticationTokenFilter！");
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行，后面还有其他过滤器
            filterChain.doFilter(request, response);
            // 所有过滤器执行完毕后，响应回来还会走到这里
            return;
        }

        // 解析token
        String id;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            id = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            // TOKEN非法
            throw new GlobalException(ResultCodeEnum.TOKEN_ILLEGAL.getMessage(),ResultCodeEnum.TOKEN_ILLEGAL.getCode());
        }

        // 从redis中获取用户信息
        String redisKey = "login:" + id;
        LoginUser loginUser = userService.getUserFromRedisById(redisKey);
        if (Objects.isNull(loginUser)) {
            // 用户未登录
            throw new GlobalException(ResultCodeEnum.USER_OFFLINE.getMessage(), ResultCodeEnum.USER_OFFLINE.getCode());
        }

        // 后面将需要一个Authentication的对象，在这里通过实现类UsernamePasswordAuthenticationToken构造这个对象
        // 选择3个参数的构造器，principal：UserDetail实现类对象，credentials：密码，认证后一般会清除，authorities：权限
        // 为什么要选择这个构造器呢？因为这个构造器中有，super.setAuthenticated(true); 标识用户为已认证。
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        // 存入SecurityContextHolder
        // 存入需要一个Authentication的对象，在登录的时候也用到过类似的方法。
        // 获取权限信息封装到Authentication
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}

