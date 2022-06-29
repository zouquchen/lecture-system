package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 修改密码vo
 * </p>
 * <br>
 * Creation Time: 2022/6/29 16:30
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class PasswordVo implements Serializable {
    private static final long serialVersionUID = 4235342321433456341L;

    private String oldPassword;
    private String newPassword;
    private String newCheckPass;

}
