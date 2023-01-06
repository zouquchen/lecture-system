package com.study.lecture.common.service;

import com.study.lecture.common.vo.LectureOrderMqVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 消息队列发送者
 * </p>
 * <br>
 * Creation Time: 2022/4/20 16:36
 *
 * @author zqc
 * @since 1.0
 */
@Service
public class MqSender {

    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     *
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息
     * @return 发送成功标志
     */
    public boolean sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return true;
    }
}
