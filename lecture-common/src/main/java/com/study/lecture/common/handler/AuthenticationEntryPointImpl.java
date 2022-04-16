package com.study.lecture.common.handler;

import com.alibaba.fastjson.JSON;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 用户未认证处理
 * </p>
 * <br>
 * Creation Time: 2022/4/6 11:10
 *
 * @author zqc
 * @since 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("经过AuthenticationEntryPointImpl！");
        R result = R.error(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重新登录");
        String json = JSON.toJSONString(result);
        // 处理异常，调用工具类
        WebUtils.renderString(response, json);
    }
}