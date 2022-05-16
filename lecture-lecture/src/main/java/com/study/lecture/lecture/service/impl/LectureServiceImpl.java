package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.*;
import com.study.lecture.lecture.mapper.LectureMapper;
import com.study.lecture.common.service.lecture.LectureService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲座发布表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@DubboService(version = "1.0")
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements LectureService {

    @Resource
    private LectureMapper lectureMapper;

    /**
     * <p> admin分页条件查询lecture列表 </p>
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    @Override
    public R lectureForAdminPageList(int page, int limit, LectureForAdminListQueryVo lectureForAdminListQueryVo) {

        String title = null;
        Long typeId = null;
        Date start = null;
        Date end = null;

        // 取出值，判断他们是否有值
        if (lectureForAdminListQueryVo != null) {
            title = lectureForAdminListQueryVo.getTitle();
            typeId = lectureForAdminListQueryVo.getTypeId();
            start = lectureForAdminListQueryVo.getStart();
            end = lectureForAdminListQueryVo.getEnd();
        }

        // 计算begin
        int begin = (page - 1) * limit;

        // 处理数据
        int total = lectureMapper.countLectureAdminListByCondition(title, typeId, start, end);
        List<LectureForAdminListVo> records = lectureMapper.getLectureAdminPageListByCondition(begin, limit, title, typeId, start, end);

        return R.ok().put("total", total).put("records", records);
    }

    /**
     * <p> user分页条件查询lecture列表 </p>
     * <p> 可查询到的讲座：已发布，并且时间在讲座开始的15分钟之前，按照讲座开始预约时间顺序排序 </p>
     *
     * @param page 当前页
     * @param limit 每页显示数量
     * @param lectureForUserListQueryVo 查询条件
     * @return 分页查询结果
     */
    @Override
    public R lectureForUserPageList(int page, int limit, LectureForUserListQueryVo lectureForUserListQueryVo) {
        String title = null;
        Long typeId = null;
        Date start = null;
        Date end = null;

        // 取出值，判断他们是否有值
        if (lectureForUserListQueryVo != null) {
            title = lectureForUserListQueryVo.getTitle();
            typeId = lectureForUserListQueryVo.getTypeId();
            start = lectureForUserListQueryVo.getStart();
            end = lectureForUserListQueryVo.getEnd();
        }

        // 计算begin
        int begin = (page - 1) * limit;

        // 处理数据
        int total = lectureMapper.countLectureUserListByCondition(title, typeId, start, end);
        List<LectureForUserListVo> records = lectureMapper.getLectureUserPageListByCondition(begin, limit, title, typeId, start, end);

        return R.ok().put("total", total).put("records", records);
    }

    /**
     * user(student)查询所有lecture列表，不分页
     * @return 查询结果
     */
    @Override
    public List<LectureForUserListVo> lectureForUserList() {
        return null;
    }


    /**
     * 添加 lecture
     * @param lecture lecture信息
     * @return 添加是否成功
     */
    @Override
    public int addLecture(Lecture lecture) {
        // TODO 事务、异常处理
        // 获取已登录用户，用于设置发布者
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 创建lecture lectureDescription对象并赋值
        lecture.setStore(lecture.getReservation());
        lecture.setCreatorId(loginUser.getUser().getId());

        // 数据库插入记录
        return lectureMapper.insert(lecture);
    }

    /**
     * 通过id获得lecture详情
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public Lecture getLectureById(Long id) {
        return lectureMapper.selectById(id);
    }

    /**
     * 更新lecture
     * @param lecture lecture信息
     * @return 更新是否成功
     */
    @Override
    public int updateLecture(Lecture lecture) {
        return lectureMapper.updateById(lecture);
    }

    /**
     * 根据id获取lecture详情, 显示详情页面
     * 与上面的方法相比，信息更全面，比如获取的是typeName而不是typeId
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public LectureForAdminInfoVo getLectureInfoById(Long id) {
        return lectureMapper.getLectureInfoById(id);
    }
}
