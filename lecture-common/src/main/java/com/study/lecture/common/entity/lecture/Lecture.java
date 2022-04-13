package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 讲座发布表
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lecture_publish")
public class Lecture implements Serializable {

    private static final long serialVersionUID = 154322635434L;

    /**
     * 讲座id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 讲座名称
     */
    private String title;

    /**
     * 讲座类型id
     */
    private Long typeId;

    /**
     * 讲座描述信息id
     */
    private Long descriptionId;

    /**
     * 主办方
     */
    private String organizer;

    /**
     * 承办方
     */
    private String undertaker;

    /**
     * 协办方
     */
    private String sponsor;

    /**
     * 讲座地点
     */
    private String space;

    /**
     * 主讲人姓名
     */
    private String speaker;

    /**
     * 讲座容纳人数
     */
    private Integer reservation;

    /**
     * 海报url
     */
    private String poster;

    /**
     * 讲座预约开始时间
     */
    private Date orderStartTime;

    /**
     * 讲座预约结束时间
     */
    private Date orderEndTime;

    /**
     * 讲座开始时间
     */
    private Date lectureStartTime;

    /**
     * 逻辑删除 1（true）已删除 0（false）未删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
