package com.study.lecture.lecture.receiver;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.lecture.common.constant.MqConstant;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureService;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.ResultCodeEnum;
import com.study.lecture.common.vo.LectureOrderMqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 处理消息队列中的任务
 * </p>
 * <br>
 * Creation Time: 2022/4/20 16:52
 *
 * @author zqc
 * @since 1.0
 */
@Service
@Slf4j
public class LectureReceiver {

    @Resource
    private LectureUserRecordService lectureUserRecordService;

    @Resource
    private LectureService lectureService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * <p>接受消息队列中的订单信息</p>
     * <p>根据LectureId和UserId创建记录</p>
     * @param lectureOrderMqVo 包含LectureId和UserId
     */
    @RabbitListener(queues = MqConstant.QUEUE_ORDER)
    public void receive(LectureOrderMqVo lectureOrderMqVo) {
        log.info("接收到消息：" + lectureOrderMqVo.toString());
        Long userId = lectureOrderMqVo.getUserId();
        Long lectureId = lectureOrderMqVo.getLectureId();

        // 从redis内查询该用户是否重复预定讲座
        LectureUserRecord record = (LectureUserRecord) redisTemplate.opsForValue().get("lecture:" + userId + ":" + lectureId);
        if (record != null) {
            return;
        }

        // 设置用户订阅讲座记录信息
        LectureUserRecord lectureUserRecord = new LectureUserRecord();
        lectureUserRecord.setUserId(userId);
        lectureUserRecord.setLectureId(lectureId);
        lectureUserRecord.setSignCodeId(12345678L);
        lectureUserRecord.setOrderTime(new Date());

        // 添加用户预约讲座的记录
        lectureUserRecordService.save(lectureUserRecord);

        // 修改订单,减少剩余可预约数量
        UpdateWrapper<Lecture> lectureUpdateWrapper = new UpdateWrapper<>();
        lectureUpdateWrapper.setSql("store = store - 1").eq("id", lectureId).gt("store", 0);

        lectureService.update(lectureUpdateWrapper);

        // 预约信息存储到redis内
        redisTemplate.opsForValue().set("lecture:" + userId + ":" + lectureId, lectureUserRecord);

        // TODO 添加事务
    }
}
