package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/6 15:09
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class MessageVo implements Serializable {
    private static final long serialVersionUID = 536547643334341L;

    /**
     * 目标用户id
     */
    private Long userId;

    /**
     * 与消息内容有关的讲座id
     */
    private Long lectureId;

    /**
     * 消息内容
     */
    private String message;
}
