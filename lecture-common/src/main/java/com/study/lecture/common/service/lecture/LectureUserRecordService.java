package com.study.lecture.common.service.lecture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureForAdminInfoVo;
import com.study.lecture.common.vo.OrderRecordOfOneLectureVo;

import java.util.Date;
import java.util.List;

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

    /**
     * 用户签到
     * @param lectureId 讲座id
     * @param username 用户名/账号
     * @return 响应类
     */
    R userSign(Long lectureId, String username);

    /**
     * 通过id获得已预约到该场讲座的所有用户
     * @param lectureForAdminInfoVo 讲座信息
     */
    void setOrderedUserListAndData(LectureForAdminInfoVo lectureForAdminInfoVo);

    /**
     * 通过id获得已签到该场讲座的所有用户
     * @param id 讲座id
     * @return 查询结果
     */
    List<OrderRecordOfOneLectureVo> getSignedUserList(Long id);

    /**
     * 分析用户关于讲座的状态
     * @param lectureState 讲座状态 0：发布， 1：结束
     * @param orderStartTime 预约开始时间
     * @param lectureUserRecord 用户预约讲座记录
     * @return 讲座状态
     */
    String analyzeLectureState(int lectureState, Date orderStartTime, LectureUserRecord lectureUserRecord);
}
