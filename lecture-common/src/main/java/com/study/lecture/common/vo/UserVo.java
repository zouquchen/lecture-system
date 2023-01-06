package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/28 15:03
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 5367238734341L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 账号状态（0正常 1停用）
     */
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户性别（0男，1女）
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色，不同角色有不同的权限 roleId
     */
    private Long role;
}
