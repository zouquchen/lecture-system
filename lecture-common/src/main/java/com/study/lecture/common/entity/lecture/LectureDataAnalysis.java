package com.study.lecture.common.entity.lecture;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 讲座数据分析表
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("lecture_data_analysis")
public class LectureDataAnalysis implements Serializable {

    private static final long serialVersionUID = 165345365365L;

    /**
     * 讲座数据分析id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 讲座id
     */
    private Long lectureId;

    /**
     * 出席人数
     */
    private Integer attendance;

    /**
     * 未出席人数
     */
    private Integer nonAttendance;

    /**
     * 迟到人数
     */
    private Integer countLate;

    /**
     * 讲座真实开始时间
     */
    private Date lectureRealStartTime;

    /**
     * 讲座真实结束时间
     */
    private Date lectureRealEndTime;

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
