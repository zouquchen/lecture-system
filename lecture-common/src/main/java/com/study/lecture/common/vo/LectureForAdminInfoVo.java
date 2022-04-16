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
public class LectureForAdminInfoVo implements Serializable {

    private static final long serialVersionUID = 13254126654856543L;

    /**
     * 讲座id lecture表
     */
    @ApiModelProperty(value = "讲座id", example = "1")
    private Long id;

    /**
     * 讲座名称 lecture表
     */
    @ApiModelProperty(value = "讲座名称", example = "讲座的艺术")
    private String title;

    /**
     * 讲座类别
     */
    @ApiModelProperty(value = "讲座类别名称", example = "法律")
    private String typeName;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "发布者姓名", example = "小张")
    private String creatorName;

    /**
     * 主办方 lecture表
     */
    @ApiModelProperty(value = "主办方", example = "东南大学研究生会")
    private String organizer;

    /**
     * 承办方 lecture表
     */
    @ApiModelProperty(value = "承办方", example = "东南大学研究生会")
    private String undertaker;

    /**
     * 协办方 lecture表
     */
    @ApiModelProperty(value = "协办方", example = "东南大学自动化学院学生会")
    private String sponsor;

    /**
     * 讲座地点 lecture表
     */
    @ApiModelProperty(value = "讲座地点", example = "四牌楼大礼堂")
    private String space;

    /**
     * 主讲嘉宾 lecture表
     */
    @ApiModelProperty(value = "主讲嘉宾", example = "xxx")
    private String speaker;

    /**
     * 可预约人数 lecture表
     */
    @ApiModelProperty(value = "可预约人数", example = "500")
    private Integer reservation;

    /**
     * 可预约人数 lecture表
     */
    @ApiModelProperty(value = "剩余预约人数", example = "188")
    private Integer store;

    /**
     * 讲座描述 lecture_description表
     */
    @ApiModelProperty(value = "讲座描述", example = "这将会是你见过最精彩的讲座")
    private String description;

    /**
     * 海报url lecture表
     */
    @ApiModelProperty(value = "海报url", example = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F200704%2F1-200F41J910.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652614434&t=a01ae28aa294ee04c2b4d30ec91a7829")
    private String poster;

    /**
     * 预约开始时间 lecture表
     */
    @ApiModelProperty(value = "预约开始时间", example = "2022-05-13T11:00:00.000+0000")
    private Date orderStartTime;

    /**
     * 预约结束时间 lecture表
     */
    @ApiModelProperty(value = "预约结束时间", example = "2022-06-13T11:00:00.000+0000")
    private Date orderEndTime;

    /**
     * 讲座开始时间 lecture表
     */
    @ApiModelProperty(value = "讲座开始时间", example = "2022-06-13T11:00:00.000+0000")
    private Date lectureStartTime;
}
