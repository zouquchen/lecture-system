package com.study.lecture.common.service.lecture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.utils.R;

/**
 * <p>
 * 讲座预约记录表 服务类
 * 可用于讲座签到
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
public interface LectureUserRecordService extends IService<LectureUserRecord> {

    /**
     * 获取用户预约讲座记录表
     * @param page 当前页
     * @param limit 每页数据个数
     * @return 记录
     */
    R getLectureUserRecordPageList(int page, int limit);

    /**
     * 根据讲座id和用户id取消预约讲座
     * @param lectureId 讲座id
     * @param userId 用户id
     * @throws Exception 异常
     */
    void orderLectureById(Long lectureId, Long userId) throws Exception;

    /**
     * 根据讲座id和用户id取消预约讲座
     * @param lectureId 讲座id
     * @param userId 用户id
     * @throws Exception 异常
     */
    void cancelLectureById(Long lectureId, Long userId) throws Exception;
}
