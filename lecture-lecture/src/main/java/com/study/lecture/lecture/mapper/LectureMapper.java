package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲座发布表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Mapper
public interface LectureMapper extends BaseMapper<Lecture> {

    /**
     * 在分页条件查询管理员可以查看的讲座列表时，查询所有符合条件的记录数
     * @param title 讲座名称
     * @param typeId 讲座类型id
     * @param startTime 查询讲座范围的开始时间
     * @param endTime 查询讲座范围的结束时间
     * @return 数据总数
     */
    int countLectureAdminListByCondition(String title, Long typeId, String startTime, String endTime);

    /**
     * 分页条件查询管理员可以查看的讲座列表
     * @param begin 起始查询的位置
     * @param limit 每页数据量
     * @param title 讲座名称
     * @param typeId 讲座类型id
     * @param startTime 查询讲座范围的开始时间
     * @param endTime 查询讲座范围的结束时间
     * @return 当前页的数据记录
     */
    List<LectureForAdminListVo> getLectureAdminPageListByCondition(int begin, int limit, String title, Long typeId, String startTime, String endTime);

    /**
     * 在分页条件查询用户（学生）可以查看的讲座列表时，查询所有符合条件的记录数
     * @param title 讲座名称
     * @param typeId 讲座类型id
     * @param startTime 查询讲座范围的开始时间
     * @param endTime 查询讲座范围的结束时间
     * @return 数据总数
     */
    int countLectureUserListByCondition(String title, Long typeId, String startTime, String endTime);

    /**
     * 分页条件查询用户（学生）可以查看的讲座列表
     * @param begin 起始查询的位置
     * @param limit 每页数据量
     * @param title 讲座名称
     * @param typeId 讲座类型id
     * @param startTime 查询讲座范围的开始时间
     * @param endTime 查询讲座范围的结束时间
     * @return 当前页的数据记录
     */
    List<LectureForUserListVo> getLectureUserPageListByCondition(int begin, int limit, String title, Long typeId, String startTime, String endTime);

    /**
     * 查询用户（学生）可以查看的全部讲座列表，用于redis缓存
     * @return 数据结果
     */
    List<LectureForUserListVo> getLectureUserList();

    /**
     * 根据id获取lecture详情, 显示详情页面 (for user)
     * @param id lecture的id
     * @return lecture详情
     */
    LectureForUserInfoVo getLectureInfoForUserById(Long id);

    /**
     * 根据id获取lecture详情, 显示详情页面 (for admin)
     * @param id lecture的id
     * @return lecture详情
     */
    LectureForAdminInfoVo getLectureInfoForAdminById(Long id);

    /**
     * 减少给定id讲座的剩余可预约数量
     * @param id 讲座id
     */
    void decreaseLectureStoreById(Long id);

    /**
     * 增加给定id讲座的剩余可预约数量
     * @param id 讲座id
     */
    void increaseLectureStoreById(Long id);
}
