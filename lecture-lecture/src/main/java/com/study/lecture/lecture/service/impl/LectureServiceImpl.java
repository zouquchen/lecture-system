package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.*;
import com.study.lecture.lecture.mapper.LectureMapper;
import com.study.lecture.common.service.lecture.LectureService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * <p> admin分页条件查询lecture列表 </p>
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    @Override
    public R lectureForAdminPageList(int page, int limit, LectureForAdminListQueryVo lectureForAdminListQueryVo) {

        String title = lectureForAdminListQueryVo.getTitle();;
        Long typeId = lectureForAdminListQueryVo.getTypeId();;
        String startTime = lectureForAdminListQueryVo.getStartTime();;
        String endTime = lectureForAdminListQueryVo.getEndTime();;

        if (title != null) {
            title = "%" + title + '%';
        }

        // 计算begin
        int begin = (page - 1) * limit;

        // 处理数据
        int total = lectureMapper.countLectureAdminListByCondition(title, typeId, startTime, endTime);
        List<LectureForAdminListVo> records = lectureMapper.getLectureAdminPageListByCondition(begin, limit, title, typeId, startTime, endTime);

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
        String title = lectureForUserListQueryVo.getTitle();
        Long typeId = lectureForUserListQueryVo.getTypeId();
        String startTime = lectureForUserListQueryVo.getStartTime();
        String endTime = lectureForUserListQueryVo.getEndTime();

        if (title != null) {
            title = "%" + title + '%';
        }

        // 计算begin
        int begin = (page - 1) * limit;

        // 处理数据
        int total = lectureMapper.countLectureUserListByCondition(title, typeId, startTime, endTime);
        List<LectureForUserListVo> records = lectureMapper.getLectureUserPageListByCondition(begin, limit, title, typeId, startTime, endTime);

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
     * 更新lecture，调用mybatis-plus的方法
     * @param lecture lecture信息
     * @return 更新是否成功
     */
    @Override
    public int updateLecture(Lecture lecture) {
        return lectureMapper.updateById(lecture);
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
     * 根据id获取lecture详情, 显示详情页面（for admin）
     * 与上面的方法相比，信息更全面，比如获取的是typeName而不是typeId
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public LectureForAdminInfoVo getLectureInfoForAdminById(Long id) {
        return lectureMapper.getLectureInfoForAdminById(id);
    }

    /**
     * 根据id获取lecture详情, 显示详情页面（for User）
     * 与上面的方法相比，信息更全面，比如获取的是typeName而不是typeId
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public LectureForUserInfoVo getLectureInfoForUserById(Long id) {
        LectureForUserInfoVo lecture = lectureMapper.getLectureInfoForUserById(id);
        Long lectureId = lecture.getId();

        // 获取已登录用户
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        // 查看该用户是否预约此讲座
        Object o = redisTemplate.opsForValue().get("lecture:" + userId + ":" + lectureId);
        if (o != null) {
            lecture.setOrdered(true);
        }

        return lecture;
    }
}
