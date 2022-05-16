package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用于用户（学生）查询已预约讲座的信息
 * </p>
 * <br>
 * Creation Time: 2022/5/14 16:49
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class LectureUserRecordVo implements Serializable {

    private static final long serialVersionUID = 53675543456341L;

    /**
     * id lecture_user_record表
     */
    private Long id;

    /**
     * 讲座id lecture_user_record表 通过讲座id查询讲座信息
     */
    private Long lectureId;

    /**
     * 用户id lecture_user_record表
     */
    private Long userId;

    /**
     * 讲座名称 lecture表
     */
    private String title;

    /**
     * 讲座类别 根据lectureId中的typeId从lectureType表查询
     */
    private String typeName;

    /**
     * 主办方 lecture表
     */
    private String organizer;

    /**
     * 状态 lecture表
     * 讲座状态 0（已发布） 1 （已结束）
     */
    private int state;

    /**
     * 前端显示的状态：
     * 未开始、已打卡、未参加
     */
    private String signState;

    /**
     * 讲座地点 lecture表
     */
    private String space;

    /**
     * 讲座开始时间 lecture表
     */
    private Date lectureStartTime;

    /**
     * 签到码id lecture_user_record表
     */
    private Long signCodeId;

    /**
     * 预约时间 lecture_user_record表
     */
    private Date orderTime;

    /**
     * 签到时间 lecture_user_record表
     */
    private Date signTime;
}
