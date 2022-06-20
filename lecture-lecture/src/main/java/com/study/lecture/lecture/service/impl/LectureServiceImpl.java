package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.constant.RedisConstant;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.*;
import com.study.lecture.lecture.mapper.LectureMapper;
import com.study.lecture.common.service.lecture.LectureService;
import com.study.lecture.lecture.mapper.LectureUserRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private LectureUserRecordMapper lectureUserRecordMapper;

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
        // 获取当前登录用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

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

        // 更新讲座预约状态
        List<Long> lectures = lectureUserRecordMapper.getAlLectureUserRecord(userId);
        Set<Long> lecturesOrderedByUser = new HashSet<>(lectures);
        for (LectureForUserListVo vo : records) {
            if (lecturesOrderedByUser.contains(vo.getId())) {
                vo.setOrderState("已预约");
            } else {
                Date now = new Date();
                if (vo.getOrderStartTime().before(now)) {
                    vo.setOrderState("可预约");
                } else {
                    vo.setOrderState("未开放");
                }
            }
        }

        return R.ok().put("total", total).put("records", records);
    }

    /**
     * <p> user(student)查询所有lecture列表，不分页 </p>
     * <p> 用于redis缓存, 启动服务时，查询所有用户可预约讲座列表 </p>
     * @return 查询结果
     */
    @Override
    public List<LectureForUserListVo> lectureForUserList() {
        return lectureMapper.getLectureUserList();
    }

    /**
     * 添加 lecture
     * @param lecture lecture信息
     * @return 添加是否成功
     */
    @Override
    public boolean addLecture(Lecture lecture) {
        // TODO 事务、异常处理
        // 获取已登录用户，用于设置发布者
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 创建lecture lectureDescription对象并赋值
        lecture.setStore(lecture.getReservation());
        lecture.setCreatorId(loginUser.getUser().getId());

        // 数据库插入记录
        int insert = lectureMapper.insert(lecture);
        if (insert > 0) {
            // 设置讲座的剩余预约数量
            redisTemplate.opsForValue().set(RedisConstant.getKeyOfLectureStore(lecture.getId()), lecture.getStore());

            // 设置讲座预约时间
            redisTemplate.opsForValue().set(RedisConstant.getKeyOfLectureOrderStartTime(lecture.getId()), lecture.getOrderStartTime());
            redisTemplate.opsForValue().set(RedisConstant.getKeyOfLectureOrderEndTime(lecture.getId()), lecture.getOrderEndTime());
            return true;
        }
        return false;
    }

    /**
     * 更新lecture，调用mybatis-plus的方法
     * @param lecture lecture信息
     * @return 更新是否成功
     */
    @Override
    public boolean updateLecture(Lecture lecture) {
        int update = lectureMapper.updateById(lecture);
        if (update > 0) {
            // 设置讲座的剩余预约数量
            redisTemplate.opsForValue().set(RedisConstant.getKeyOfLectureStore(lecture.getId()), lecture.getStore());

            // 设置讲座预约时间
            redisTemplate.opsForValue().set(RedisConstant.getKeyOfLectureOrderStartTime(lecture.getId()), lecture.getOrderStartTime());
            redisTemplate.opsForValue().set(RedisConstant.getKeyOfLectureOrderEndTime(lecture.getId()), lecture.getOrderEndTime());
            return true;
        }
        return false;
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
     * 根据id获取lecture详情, 显示详情页面（for User）
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
        Object o = redisTemplate.opsForValue().get(RedisConstant.getKeyOfUserRecord(userId, lectureId));
        if (o != null) {
            lecture.setOrdered(true);
        }

        return lecture;
    }

    /**
     * 根据id获取lecture详情（讲座详情、预约该讲座的用户列表）, 显示详情页面（for admin），
     * 与上面的方法相比，信息更全面，比如获取的是typeName而不是typeId
     * @param id lecture的id
     * @return 查询结果
     */
    @Override
    public LectureForAdminInfoVo getLectureInfoForAdminById(Long id) {
        // 根据id查询讲座详情
        return lectureMapper.getLectureInfoForAdminById(id);
    }
}
