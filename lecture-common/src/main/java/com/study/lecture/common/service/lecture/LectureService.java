package com.study.lecture.common.service.lecture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureForAdminInfoVo;
import com.study.lecture.common.vo.LectureForAdminListQueryVo;
import com.study.lecture.common.vo.LectureForUserListQueryVo;
import com.study.lecture.common.vo.LectureForUserListVo;

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
     * user(student)查询所有lecture列表，不分页
     * @return 查询结果
     */
    List<LectureForUserListVo> lectureForUserList();

    /**
     * 添加 lecture
     * @param lecture lecture信息
     * @return 添加是否成功
     */
    int addLecture(Lecture lecture);

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
    int updateLecture(Lecture lecture);

    /**
     * 通过id获得lecture详情
     * @param id lecture的id
     * @return 查询结果
     */
    LectureForAdminInfoVo getLectureInfoById(Long id);

}