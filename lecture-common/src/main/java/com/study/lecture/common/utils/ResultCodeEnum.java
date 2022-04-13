package com.study.lecture.common.utils;

import lombok.Getter;

/**
 * <p>
 * 统一返回结果状态信息类
 * </p>
 * <br>
 * Creation Time: 2022/4/13 16:40
 *
 * @author zqc
 * @since 1.0
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(20000,"成功"),

    /**
     * 未知异常
     */
    ERROR(50000, "未知异常，请联系管理员"),

    /**
     * TOKEN非法
     */
    TOKEN_ILLEGAL(50008,"TOKEN非法"),

    /**
     * 其他客户端登录
     */
    LOGIN_ANOTHER(50012, "其他客户端登录"),

    /**
     * TOKEN过期
     */
    TOKEN_EXPIRE(50014, "TOKEN过期"),

    /**
     * 用户未登录
     */
    USER_OFFLINE(500100, "用户未登录")
    ;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态码含义
     */
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
