package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 讲座预约记录表
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lecture_user_record")
public class LectureUserRecord implements Serializable {

    private static final long serialVersionUID = 15434264574L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 讲座id
     */
    private Long lectureId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 签到码id
     */
    private Long signCodeId;

    /**
     * 预约时间
     */
    private Date orderTime;

    /**
     * 签到时间
     */
    private Date signTime;

    /**
     * 逻辑删除 1（true）已删除  0（false）未删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
