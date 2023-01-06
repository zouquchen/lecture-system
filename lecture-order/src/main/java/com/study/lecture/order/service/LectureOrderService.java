package com.study.lecture.order.service;

import com.study.lecture.common.utils.R;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/18 21:16
 *
 * @author zqc
 * @since 1.0
 */
public interface LectureOrderService {

    /**
     * 根据讲座id预约讲座
     * @param lectureId 讲座id
     * @return 响应类
     */
    R orderLectureById(Long lectureId);

    /**
     * 根据讲座id取消讲座
     * @param lectureId 讲座id
     * @return 响应类
     */
    R cancelLectureById(Long lectureId);
}
