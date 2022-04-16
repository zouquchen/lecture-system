package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 讲座预约表
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lecture_order")
public class LectureOrder implements Serializable {

    private static final long serialVersionUID = 15436545245L;

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
     * 讲座名称
     */
    private String title;

    /**
     * 剩余预约数量
     */
    private Integer store;

    /**
     * 讲座预约开始时间
     */
    private Date orderStartTime;

    /**
     * 讲座预约结束时间
     */
    private Date orderEndTime;

    /**
     * 逻辑删除 1（true）已删除 0（false）未删除
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
