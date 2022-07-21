package com.study.lecture.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/6 21:42
 *
 * @author zqc
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageMqVo implements Serializable {
    private static final long serialVersionUID = 536547643332342L;

    /**
     * 被评论或点赞的评论id
     */
    private Long commentId;

    /**
     * 评论/点赞者的用户名
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 时间
     */
    private Date date;
}
