package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/16 15:10
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class RoleListVo implements Serializable {
    private static final long serialVersionUID = 423534235433456341L;

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;
}
