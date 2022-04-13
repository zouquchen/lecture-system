package com.study.lecture.common.exception;


/**
 * <p>
 * 自定义异常
 * </p>
 * <br>
 * Creation Time: 2022/4/13 16:57
 *
 * @author zqc
 * @since 1.0
 */
public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = 1432423442334L;

    private String msg;
    private int code = 50000;

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GlobalException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GlobalException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
