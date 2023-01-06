package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.constant.LectureStateEnum;
import com.study.lecture.common.constant.RedisConstant;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.utils.ResultCodeEnum;
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
import java.util.*;

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
    private LectureUserRecordService lectureUserRecordService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * <p> 管理员分页条件查询lecture列表 </p>
     * <p> admin可以查询到所有的lecture列表 </p>
     * <p> manager只能查询到它发布的lecture列表 </p>
     * @param page 当前页
     * @param limit 每页显示数量
     * @param lectureForAdminListQueryVo 查询条件
     * @return 分页查询结果
     */
    @Override
    public R lectureForAdminPageList(int page, int limit, LectureForAdminListQueryVo lectureForAdminListQueryVo) {
        // 获取登录用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        if (permissions.size() == 0) {
            throw new GlobalException(ResultCodeEnum.ERROR);
        }
        String role = permissions.get(0);
        // 若为admin角色，userId为null，否则userId为当前用户id
        Long userId = "admin".equals(role) ? null : loginUser.getUser().getId();

        String title = lectureForAdminListQueryVo.getTitle();
        Long typeId = lectureForAdminListQueryVo.getTypeId();
        String startTime = lectureForAdminListQueryVo.getStartTime();
        String endTime = lectureForAdminListQueryVo.getEndTime();

        if (title != null) {
            title = "%" + title + '%';
        }

        // 计算begin
        int begin = (page - 1) * limit;

        // 处理数据
        int total = lectureMapper.countLectureAdminListByCondition(title, typeId, startTime, endTime, userId);
        List<LectureForAdminListVo> records = lectureMapper.getLectureAdminPageListByCondition(begin, limit, title, typeId, startTime, endTime, userId);

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
        List<LectureUserRecord> allLectureUserRecord = lectureUserRecordMapper.getAllLectureUserRecord(userId);
        Map<Long, LectureUserRecord> map = new HashMap<>(allLectureUserRecord.size());
        for (LectureUserRecord lectureUserRecord : allLectureUserRecord) {
            map.put(lectureUserRecord.getLectureId(), lectureUserRecord);
        }

        for (LectureForUserListVo vo : records) {
            String s = lectureUserRecordService.analyzeLectureState(vo.getState(), vo.getOrderStartTime(), map.getOrDefault(vo.getId(), null));
            vo.setDisplayState(s);
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
        LectureUserRecord lectureUserRecord = lectureUserRecordMapper.getLectureUserRecord(lectureId, userId);

        // 讲座预约开始时间
        Date orderStartTime = lecture.getOrderStartTime();
        // 讲座状态：0发布、1结束
        int lectureState = lecture.getState();

        // 分析用户关于讲座的状态
        String displayState = lectureUserRecordService.analyzeLectureState(lectureState, orderStartTime, lectureUserRecord);

        lecture.setDisplayState(displayState);

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

    /**
     * 逻辑删除讲座
     * @param id 讲座id
     */
    @Override
    public void deleteLecture(Long id) {
        // 获取已登录用户
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        List<String> permissions = loginUser.getPermissions();
        if (permissions.size() == 0) {
            throw new GlobalException(ResultCodeEnum.ERROR);
        }
        String role = permissions.get(0);
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Lecture lecture = lectureMapper.selectOne(queryWrapper);

        // 判断删除者是不是创建者或admin
        if (lecture.getCreatorId().equals(userId) || "admin".equals(role)) {
            lectureMapper.logicallyDeleteLectureById(id);
        } else {
            throw new GlobalException(ResultCodeEnum.PERMISSION_DENIED);
        }
    }


}
