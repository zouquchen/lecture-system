package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评论
 * </p>
 * <br>
 * Creation Time: 2022/7/1 14:11
 *
 * @author zqc
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lecture_comment")
public class LectureComment implements Serializable {

    private static final long serialVersionUID = 1653423365365L;

    /**
     * 讲座数据分析id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 讲座id
     */
    private Long lectureId;

    /**
     * 父评论id，可以为null
     */
    private Long parentId;

    /**
     * 根评论id，可以为null
     */
    private Long rootParentId;

    /**
     * 评论内容
     */
    private String content;

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
