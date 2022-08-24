package com.study.lecture.lecture.receiver;

import com.study.lecture.common.constant.MqConstant;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureService;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.vo.LectureOrderMqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
@Slf4j(topic = "LectureReceiver")
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
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_ORDER, durable = "true", type = "topic"),
            key = MqConstant.ROUTE_ORDER
    ))
    public void receive(LectureOrderMqVo lectureOrderMqVo) {
        // log.info("接收到消息：" + lectureOrderMqVo.toString());

        Long userId = lectureOrderMqVo.getUserId();
        Long lectureId = lectureOrderMqVo.getLectureId();

        try {
            lectureUserRecordService.orderLectureById(lectureId, userId);
            // TODO Zookeeper 作为分布式协调，删除表示“某用户正在预约某讲座”的节点
        } catch (Exception exception) {
            // TODO Redis 剩余可预约数量加 1
            // TODO 消费失败留存记录
            log.error("讲座预约失败");
            // throw new GlobalException("讲座预约失败");
        }
    }
}
