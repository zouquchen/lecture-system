package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员列表查询时的条件
 * </p>
 * <br>
 * Creation Time: 2022/5/4 15:51
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class LectureForAdminListQueryVo implements Serializable {

    private static final long serialVersionUID = 132575806435576L;

    /**
     * 活动名称
     */
    private String title;

    /**
     * 讲座类型id
     */
    private Long typeId;

    /**
     * 根据讲座开始时间范围查询，开始时间
     */
    private Date start;

    /**
     * 根据讲座开始时间范围查询，截至时间
     */
    private Date end;
}
