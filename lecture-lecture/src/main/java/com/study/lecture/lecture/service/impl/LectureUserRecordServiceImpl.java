package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.constant.LectureStateEnum;
import com.study.lecture.common.constant.RedisConstant;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.utils.ResultCodeEnum;
import com.study.lecture.common.vo.LectureForAdminInfoVo;
import com.study.lecture.common.vo.LectureUserRecordVo;
import com.study.lecture.common.vo.OrderRecordOfOneLectureVo;
import com.study.lecture.lecture.mapper.LectureMapper;
import com.study.lecture.lecture.mapper.LectureUserRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲座预约记录表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@DubboService(version = "1.0")
public class LectureUserRecordServiceImpl extends ServiceImpl<LectureUserRecordMapper, LectureUserRecord> implements LectureUserRecordService {

    @Resource
    private LectureUserRecordMapper lectureUserRecordMapper;

    @Resource
    private LectureMapper lectureMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取用户预约讲座记录表
     * @param page 当前页
     * @param limit 每页数据个数
     * @return 记录
     */
    @Override
    public R getLectureUserRecordPageList(int page, int limit) {
        // 获取已登录用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        // 查询数据
        int begin = limit * (page - 1);
        List<LectureUserRecordVo> records = lectureUserRecordMapper.getLectureUserRecordPageList(userId, begin, limit);
        for(LectureUserRecordVo vo : records) {

            // 分析讲座关于用户的状态
            LectureUserRecord lectureUserRecord = new LectureUserRecord();
            BeanUtils.copyProperties(vo, lectureUserRecord);
            String s = analyzeLectureState(vo.getState(), vo.getOrderTime(), lectureUserRecord);

            vo.setDisplayState(s);
        }
        int total = lectureUserRecordMapper.countLectureUserRecord(userId);

        return R.ok().put("records", records).put("total", total);
    }

    /**
     * 根据讲座id和用户id取消预约讲座
     * @param lectureId 讲座id
     * @param userId 用户id
     * @throws Exception 异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void orderLectureById(Long lectureId, Long userId) throws Exception {
        // 从redis内查询该用户是否重复预定讲座
        LectureUserRecord record = (LectureUserRecord) redisTemplate.opsForValue().get(RedisConstant.getKeyOfUserRecord(userId, lectureId));
        if (record != null) {
            throw new GlobalException(ResultCodeEnum.REPEAT_ORDER);
        }

        // 设置用户订阅讲座记录信息
        LectureUserRecord lectureUserRecord = new LectureUserRecord();
        lectureUserRecord.setUserId(userId);
        lectureUserRecord.setLectureId(lectureId);
        lectureUserRecord.setSignCodeId(12345678L);
        lectureUserRecord.setOrderTime(new Date());

        // 添加用户预约讲座的记录
        lectureUserRecordMapper.insert(lectureUserRecord);

        // 修改订单,减少剩余可预约数量
        lectureMapper.decreaseLectureStoreById(lectureId);

        // 预约信息存储到redis内
        redisTemplate.opsForValue().set(RedisConstant.getKeyOfUserRecord(userId, lectureId), lectureUserRecord);
    }


    /**
     * 根据讲座id和用户id取消预约讲座
     * @param lectureId 讲座id
     * @param userId 用户id
     * @throws Exception 异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void cancelLectureById(Long lectureId, Long userId) throws Exception {
        // 根据讲座id和用户id删除用户预约讲座记录
        lectureUserRecordMapper.deleteLectureUserRecord(lectureId, userId);

        // 增加给定id讲座的剩余可预约数量
        lectureMapper.increaseLectureStoreById(lectureId);

        // 删除redis内的记录
        redisTemplate.delete(RedisConstant.getKeyOfUserRecord(userId, lectureId));
    }

    /**
     * 用户签到
     * @param lectureId 讲座id
     * @param username 用户名/账号
     * @return 响应类
     */
    @Override
    public R userSign(Long lectureId, String username) {
        int sign = lectureUserRecordMapper.userSign(lectureId, username);
        if (sign > 0) {
            return R.ok();
        } else {
            return R.error("签到失败");
        }
    }


    /**
     * 通过id获得已预约到该场讲座的所有用户
     * @param lectureForAdminInfoVo 讲座信息
     * @return 查询结果
     */
    @Override
    public void setOrderedUserListAndData(LectureForAdminInfoVo lectureForAdminInfoVo) {
        // 根据id查询预约该讲座的所有用户信息
        Long id = lectureForAdminInfoVo.getId();
        List<OrderRecordOfOneLectureVo> recordList = lectureUserRecordMapper.getOrderRecordOfOneLectureListById(id);

        int userCount = lectureForAdminInfoVo.getReservation() - lectureForAdminInfoVo.getStore();
        int signCount = 0;
        int notAttendCount = 0;

        // 修改list中每个用户的状态
        int state = lectureForAdminInfoVo.getState();
        for (OrderRecordOfOneLectureVo vo : recordList) {
            if (state == 0) {
                vo.setState("等待开始");
            } else {
                if (vo.getSignTime() == null) {
                    vo.setState("未参加");
                    notAttendCount++;
                } else {
                    vo.setState("已签到");
                    signCount++;
                }
            }
        }
        lectureForAdminInfoVo.setUserCount(userCount);
        lectureForAdminInfoVo.setSignCount(signCount);
        lectureForAdminInfoVo.setNotAttendCount(notAttendCount);
        lectureForAdminInfoVo.setUserList(recordList);
    }

    /**
     * 通过id获得已签到该场讲座的所有用户
     * @param id 讲座id
     * @return 查询结果
     */
    @Override
    public List<OrderRecordOfOneLectureVo> getSignedUserList(Long id) {
        return lectureUserRecordMapper.getSignedUserOfOneLectureListById(id);
    }

    /**
     * 分析用户关于讲座的状态
     * @param lectureState 讲座状态 0：发布， 1：结束
     * @param orderStartTime 预约开始时间
     * @param lectureUserRecord 用户预约讲座记录
     * @return 讲座状态
     */
    @Override
    public String analyzeLectureState(int lectureState, Date orderStartTime, LectureUserRecord lectureUserRecord) {
        Date now = new Date();
        String displayState;

        // 讲座开始预约时间还未到
        if (orderStartTime.after(now)) {
            displayState = LectureStateEnum.NOT_OPEN.getState();
        }
        // 讲座开始预约时间已到
        else {
            // 讲座状态：已发布
            if (lectureState == 0) {
                // 无预约记录
                if (lectureUserRecord == null) {

                    displayState = LectureStateEnum.NOT_ORDER.getState();
                }
                // 有预约记录
                else {
                    // 未签到
                    if (lectureUserRecord.getSignTime() == null) {
                        displayState = LectureStateEnum.ORDERED.getState();
                    }
                    // 已签到
                    else {
                        displayState = LectureStateEnum.SIGNED.getState();
                    }
                }
            }
            // 讲座状态：已结束
            else {
                // 无预约记录
                if (lectureUserRecord == null) {
                    displayState = LectureStateEnum.FINISH.getState();
                }
                // 有预约记录
                else {
                    // 未签到
                    if (lectureUserRecord.getSignTime() == null) {
                        displayState = LectureStateEnum.NOT_ATTEND.getState();
                    }
                    // 已签到
                    else {
                        displayState = LectureStateEnum.SIGNED.getState();
                    }
                }
            }
        }
        return displayState;
    }


}