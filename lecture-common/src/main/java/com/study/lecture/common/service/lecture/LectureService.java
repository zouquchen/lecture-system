package com.study.lecture.common.service.lecture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureForAdminInfoVo;


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
     * 分页查询
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    R pageList(int page, int limit);

    /**
     * admin分页查询lecture列表
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    R adminPageList(int page, int limit);

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
