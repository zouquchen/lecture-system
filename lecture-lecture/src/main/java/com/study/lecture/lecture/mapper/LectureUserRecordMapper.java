package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.vo.LectureUserRecordVo;
import com.study.lecture.common.vo.OrderRecordOfOneLectureVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 讲座预约记录表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Mapper
public interface LectureUserRecordMapper extends BaseMapper<LectureUserRecord> {

    /**
     * 根据id查询该用户预约过的所有讲座id
     * 用于更新用户讲座列表的讲座状态（未开始、可预约、已预约）
     * @param userId 用户id
     * @return
     */
    List<LectureUserRecord> getAllLectureUserRecord(Long userId);

    /**
     * 根据用户id查询该用户已预约过的讲座，分页查询
     * @param userId 用户id
     * @param begin 分页查询，数据起始位置
     * @param limit 分页查询，数据结束位置
     * @return 查询列表
     */

    List<LectureUserRecordVo> getLectureUserRecordPageList(Long userId, int begin, int limit);

    /**
     * 根据用户id查询该用户已预约过的讲座的数量
     * @param userId 用户id
     * @return 查询列表
     */
    int countLectureUserRecord(Long userId);

    /**
     * 根据讲座id和用户id获取用户预约讲座记录
     * @param lectureId 讲座id
     * @param userId 用户id
     * @return 用户预约记录
     */
    LectureUserRecord getLectureUserRecord(Long lectureId, Long userId);

    /**
     * 根据讲座id和用户id删除用户预约讲座记录
     * @param lectureId 讲座id
     * @param userId 用户id
     */
    void deleteLectureUserRecord(Long lectureId, Long userId);

    /**
     * 根据id获取预约此讲座的所有用户信息
     * @param id lecture的id
     * @return 所有预约此讲座的用户信息
     */
    List<OrderRecordOfOneLectureVo> getOrderRecordOfOneLectureListById(Long id);

    /**
     * 根据id获取预约且签到此讲座的所有用户信息
     * @param id lecture的id
     * @return 所有预约此讲座的用户信息
     */
    List<OrderRecordOfOneLectureVo> getSignedUserOfOneLectureListById(Long id);

    /**
     * 用户签到
     * @param lectureId 讲座id
     * @param username 用户名/账号
     * @return 成功标志
     */
    int userSign(Long lectureId, String username);
}
