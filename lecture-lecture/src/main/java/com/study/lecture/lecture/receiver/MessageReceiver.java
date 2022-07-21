package com.study.lecture.lecture.receiver;

import com.study.lecture.common.constant.MqConstant;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureCommentService;
import com.study.lecture.common.vo.MessageMqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 使用了消息队列的异步功能
 * 监听消息队列中的消息，接收到消息后进一步处理
 * </p>
 * <br>
 * Creation Time: 2022/7/6 21:52
 *
 * @author zqc
 * @since 1.0
 */
@Service
@Slf4j(topic = "MessageReceiver")
public class MessageReceiver {
    @Resource
    private LectureCommentService lectureCommentService;

    /**
     * 接受消息队列中的消息，用于系统通知
     * @param messageMqVo 信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_MESSAGE_COMMENT, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_MESSAGE_COMMENT, durable = "true", type = "topic"),
            key = MqConstant.ROUTE_MESSAGE_COMMENT
    ))
    public void receiveComment(MessageMqVo messageMqVo) {
        log.debug("消费消息队列中的评论消息！");
        try {
            lectureCommentService.addCommentMessage(messageMqVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("消费消息失败[系统评论消息]！");
        }
    }

    /**
     * 接受消息队列中的消息，用于系统通知
     * @param messageMqVo 信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_MESSAGE_LIKES, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_MESSAGE_LIKES, durable = "true", type = "topic"),
            key = MqConstant.ROUTE_MESSAGE_LIKES
    ))
    public void receiveLikes(MessageMqVo messageMqVo) {
        log.debug("消费消息队列中的点赞消息！");
        try {
            lectureCommentService.addLikeMessage(messageMqVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("消费消息失败[系统点赞消息]！");
        }
    }
}
