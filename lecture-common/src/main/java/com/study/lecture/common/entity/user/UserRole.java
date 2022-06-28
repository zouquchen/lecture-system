package com.study.lecture.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户角色关联表
 * </p>
 * <br>
 * Creation Time: 2022/6/28 15:16
 *
 * @author zqc
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 53325434734341L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}
