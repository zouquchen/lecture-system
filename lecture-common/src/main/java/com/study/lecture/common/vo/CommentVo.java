package com.study.lecture.common.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/1 14:22
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 322547623456543L;

    /**
     * 讲座数据分析id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 讲座id
     */
    private Long lectureId;

    /**
     * 父评论id，可以为null
     */
    private Long parentId;

    /**
     * 父评论username，可以为null
     */
    private String parentName;

    /**
     * 根评论id，可以为null
     */
    private Long rootParentId;

    /**
     * 根评论username，可以为null
     */
    private String rootParentName;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 讲座子评论
     */
    private List<CommentVo> children;
}
