package com.study.lecture.common.handler;

import com.study.lecture.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.StringJoiner;

/**
 * <p>
 * 捕获处理参数校验的异常，并返回前端
 * </p>
 * <br>
 * Creation Time: 2022/5/18 21:32
 *
 * @author zqc
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
public class ValidParamExceptionHandler {

    /**
     * 捕获处理参数校验的异常，并返回前端
     * @param e 异常信息
     * @return 异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringJoiner errorMessage = new StringJoiner("; ", "错误: ", "");
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            errorMessage.add(bindingResult.getFieldErrors().get(i).getDefaultMessage());
        }

        return R.error(errorMessage.toString());
    }
}
