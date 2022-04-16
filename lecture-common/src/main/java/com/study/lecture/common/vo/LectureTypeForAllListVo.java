package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 所有权限都可使用
 * 获取活动类型列表
 * </p>
 * <br>
 * Creation Time: 2022/4/15 22:04
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class LectureTypeForAllListVo implements Serializable {

    private static final long serialVersionUID = 4235342543456341L;

    /**
     * 讲座类型id
     */
    private Long id;

    /**
     * 讲座类型名称
     */
    private String name;
}
