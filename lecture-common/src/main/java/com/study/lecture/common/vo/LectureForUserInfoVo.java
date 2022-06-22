package com.study.lecture.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 所有权限都可使用
 * 获取活动详情
 * </p>
 * <br>
 * Creation Time: 2022/4/15 18:28
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class LectureForUserInfoVo implements Serializable {

    private static final long serialVersionUID = 13254126654856543L;

    /**
     * 讲座id lecture表
     */
    private Long id;

    /**
     * 讲座名称 lecture表
     */
    private String title;

    /**
     * 讲座类别
     */
    private String typeName;

    /**
     * 创建者id
     */
    private String creatorName;

    /**
     * lecture定义的state包含 0：已发布， 1：已结束
     * 需要根据时间推算
     */
    private int state;

    /**
     * 显示的状态
     */
    private String displayState;

    /**
     * 是否已被用户预约
     */
    private boolean ordered;

    /**
     * 是否已签到
     */
    private boolean signed;

    /**
     * 主办方 lecture表
     */
    private String organizer;

    /**
     * 承办方 lecture表
     */
    private String undertaker;

    /**
     * 协办方 lecture表
     */
    private String sponsor;

    /**
     * 讲座地点 lecture表
     */
    private String space;

    /**
     * 主讲嘉宾 lecture表
     */
    private String speaker;

    /**
     * 可预约人数 lecture表
     */
    private Integer reservation;

    /**
     * 可预约人数 lecture表
     */
    private Integer store;

    /**
     * 讲座描述 lecture_description表
     */
    private String description;

    /**
     * 海报url lecture表
     */
    private String poster;

    /**
     * 预约开始时间 lecture表
     */
    private Date orderStartTime;

    /**
     * 预约结束时间 lecture表
     */
    private Date orderEndTime;

    /**
     * 讲座开始时间 lecture表
     */
    private Date lectureStartTime;
}
