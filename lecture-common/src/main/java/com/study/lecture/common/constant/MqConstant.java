package com.study.lecture.common.constant;


/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/21 14:52
 *
 * @author zqc
 * @since 1.0
 */
public class MqConstant {

    /**
     * 预定讲座相关
     */
    public static final String QUEUE_ORDER = "orderLectureQueue";
    public static final String EXCHANGE_ORDER = "orderLectureExchange";
    public static final String ROUTE_ORDER = "orderLecture.#";
}
