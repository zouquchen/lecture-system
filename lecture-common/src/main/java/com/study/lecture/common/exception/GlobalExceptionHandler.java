package com.study.lecture.common.exception;

import com.study.lecture.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * 异常处理器
 * </p>
 * <br>
 * Creation Time: 2022/4/13 17:01
 *
 * @author zqc
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GlobalException.class)
    public R handleGlobalException(GlobalException e){
        logger.error(e.getMsg(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }



}
