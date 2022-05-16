package com.study.lecture.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/20 17:32
 *
 * @author zqc
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureOrderMqVo implements Serializable {
    private static final long serialVersionUID = 1325051592856543L;
    private Long userId;
    private Long lectureId;
}
