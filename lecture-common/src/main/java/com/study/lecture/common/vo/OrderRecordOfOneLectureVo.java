package com.study.lecture.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员查看详情页面时可以查看哪个用户预约了此讲座，每个预约此讲座的用户信息
 * </p>
 * <br>
 * Creation Time: 2022/6/15 16:24
 *
 * @author zqc
 * @since 1.0
 */
@Data
public class OrderRecordOfOneLectureVo implements Serializable {
    private static final long serialVersionUID = 53675543234341L;

    private Long userId;
    private String username;
    private Date orderTime;
    private Date signTime;
    private String state;
}
