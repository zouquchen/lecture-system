package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
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
@TableName("lecture")
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
    @NotNull(message = "请输入讲座名称")
    @Length(max = 30, message = "讲座名称长度不符合要求，请确保长度在3到30之间")
    @Length(min = 3, message = "讲座名称长度不符合要求，请确保长度在3到30之间")
    private String title;

    /**
     * 讲座类型id
     */
    @NotNull(message = "请选择讲座类型")
    private Long typeId;

    /**
     * 讲座状态 0（已发布） 1 （已结束）
     */
    private int state;
    /**
     * 讲座描述信息
     */
    @Length(max = 200)
    private String description;

    /**
     * 创建者id，只有创建者和管理才能修改信息
     */
    private Long creatorId;

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
    @NotNull(message = "请填写讲座地点")
    @Length(max = 30, message = "地点长度不符合要求，请确保长度在3到30之间")
    @Length(min = 3, message = "地点长度不符合要求，请确保长度在3到30之间")
    private String space;

    /**
     * 主讲人姓名
     */
    private String speaker;

    /**
     * 讲座容纳人数
     */
    @NotNull(message = "请填写讲座容纳人数")
    private Integer reservation;

    /**
     * 剩余预约数量
     */
    private Integer store;

    /**
     * 海报url
     */
    private String poster;

    /**
     * 讲座预约开始时间
     */
    @NotNull(message = "请填写讲座预约开始时间")
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
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
