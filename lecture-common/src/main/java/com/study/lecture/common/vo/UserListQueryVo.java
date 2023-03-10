package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/16 14:10
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class UserListQueryVo implements Serializable {
    private static final long serialVersionUID = 5361545434341L;

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 用户性别（0男，1女）
     */
    private Boolean sex;

    /**
     * 角色，不同角色有不同的权限
     */
    private String role;

    /**
     * 角色所对应的id
     */
    private Long roleId;
}
