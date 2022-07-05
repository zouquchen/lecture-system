package com.study.lecture.common.constant;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/20 10:02
 *
 * @author zqc
 * @since 1.0
 */
public class RedisConstant {
    /**
     * Redis键值相关
     */
    public static final String LECTURE_ORDER_START_TIME = "lectureInfo:orderStartTime:";
    public static final String LECTURE_ORDER_END_TIME = "lectureInfo:orderEndTime:";
    public static final String LECTURE_STORE = "lectureInfo:stock:";
    public static final String LECTURE_USER_RECORD = "userRecord:";

    public static final String LECTURE_COMMENT_LIKES = "commentLikes:";

    /**
     * 用于redis，讲座开始时间的key
     * @param lectureId 讲座id
     * @return key的字符串
     */
    public static String getKeyOfLectureOrderStartTime(Long lectureId) {
        return LECTURE_ORDER_START_TIME + lectureId;
    }

    /**
     * 用于redis，讲座开始时间的key
     * @param lectureId 讲座id
     * @return key的字符串
     */
    public static String getKeyOfLectureOrderEndTime(Long lectureId) {
        return LECTURE_ORDER_END_TIME + lectureId;
    }

    /**
     * 用于redis，讲座的剩余可预约数量
     * @param lectureId 讲座id
     * @return key的字符串
     */
    public static String getKeyOfLectureStore(Long lectureId) {
        return LECTURE_STORE + lectureId;
    }

    /**
     * 用户预约讲座记录
     * @param userId 用户id
     * @param lectureId 讲座id
     * @return key的字符串
     */
    public static String getKeyOfUserRecord(Long userId, Long lectureId) {
        return LECTURE_USER_RECORD + userId + ":" + lectureId;
    }

    /**
     * 评论点赞（set）
     * @param commentId 评论id
     * @return 评论点赞的key
     */
    public static String getKeyOfLectureCommentLikes(Long commentId) {
        return LECTURE_COMMENT_LIKES + commentId;
    }
}
