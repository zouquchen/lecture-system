package com.study.lecture.common.service.lecture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.*;

import java.util.List;


/**
 * <p>
 * 讲座发布表 服务类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
public interface LectureService extends IService<Lecture> {

    /**
     * admin分页查询lecture列表
     * @param page 当前页
     * @param limit 每页显示数量
     * @param lectureForAdminListQueryVo 查询条件
     * @return 分页查询结果
     */
    R lectureForAdminPageList(int page, int limit, LectureForAdminListQueryVo lectureForAdminListQueryVo);

    /**
     * user(student)分页查询lecture列表
     * @param page 当前页
     * @param limit 每页显示数量
     * @param lectureForUserListQueryVo 查询条件
     * @return 分页查询结果
     */
    R lectureForUserPageList(int page, int limit, LectureForUserListQueryVo lectureForUserListQueryVo);

    /**
     * user(student)查询所有lecture列表，不分页，用于redis缓存
     * @return 查询结果
     */
    List<LectureForUserListVo> lectureForUserList();

    /**
     * 添加 lecture
     * @param lecture lecture信息
     * @return 添加是否成功
     */
    boolean addLecture(Lecture lecture);

    /**
     * 通过id获得lecture详情
     * @param id lecture的id
     * @return 查询结果
     */
    Lecture getLectureById(Long id);

    /**
     * 更新lecture
     * @param lecture lecture信息
     * @return 更新是否成功
     */
    boolean updateLecture(Lecture lecture);

    /**
     * 通过id获得lecture详情 (for User)
     * @param id lecture的id
     * @return 查询结果
     */
    LectureForUserInfoVo getLectureInfoForUserById(Long id);

    /**
     * 通过id获得lecture详情，包含预约该讲座的用户信息 (for admin)
     * @param id lecture的id
     * @return 查询结果
     */
    LectureForAdminInfoVo getLectureInfoForAdminById(Long id);

    /**
     * 逻辑删除讲座
     * @param id 讲座id
     */
    void deleteLecture(Long id);
}
