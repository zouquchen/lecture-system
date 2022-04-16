package com.study.lecture.common.handler;

import com.alibaba.fastjson.JSON;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 用户权限不足处理
 * </p>
 * <br>
 * Creation Time: 2022/4/6 11:20
 *
 * @author zqc
 * @since 1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("经过AccessDeniedHandlerImpl！");
        R result = R.error(HttpStatus.FORBIDDEN.value(), "您的权限不足");
        String json = JSON.toJSONString(result);
        // 处理异常，调用工具类
        WebUtils.renderString(response, json);
    }
}
