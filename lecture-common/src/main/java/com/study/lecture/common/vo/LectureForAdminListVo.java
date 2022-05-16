package com.study.lecture.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/14 12:42
 *
 * @author zqc
 * @since 1.0
 */
@ApiModel(value = "Lecture查询对象", description = "管理员查询讲座列表对象")
@Data
public class LectureForAdminListVo implements Serializable {

    private static final long serialVersionUID = 1325476654856543L;

    /**
     * 讲座id lecture表
     */
    @ApiModelProperty(value = "讲座id", example = "1")
    private Long id;

    /**
     * 讲座名称 lecture表
     */
    private String title;

    /**
     * lecture定义的state包含 0：已发布， 1：已结束
     * 需要根据时间推算
     */
    private int state;

    /**
     * 讲座类别 lectureType表
     */
    private String typeName;

    /**
     * 讲座地点 lecture表
     */
    private String space;

    /**
     * 主办方 lecture表
     */
    private String organizer;

    /**
     * 剩余预约数量 lectureOrder表
     */
    private Integer store;
    /**
     * 可预约人数 lecture表
     */
    private Integer reservation;

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
