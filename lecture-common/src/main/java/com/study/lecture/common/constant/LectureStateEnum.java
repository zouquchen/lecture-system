package com.study.lecture.common.constant;

import lombok.Getter;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/21 22:15
 *
 * @author zqc
 * @since 1.0
 */
@Getter
public enum LectureStateEnum {

    /**
     * 用户讲座参与状态
     * 讲座状态：未开放、已开放、已结束
     * 用户预约状态：已预约、未预约
     * 用户参与状态：已签到、未参加
     */
    NOT_OPEN("未开放"),
    OPEN("已开放"),
    FINISH("已结束"),
    ORDERED("已预约"),
    NOT_ORDER("未预约"),
    SIGNED("已签到"),
    NOT_ATTEND("缺席");

    /**
     * 用户讲座参与状态
     */
    private final String state;

    LectureStateEnum(String state) {
        this.state = state;
    }
}
