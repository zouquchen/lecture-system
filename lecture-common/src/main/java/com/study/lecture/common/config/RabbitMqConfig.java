package com.study.lecture.common.config;

import com.study.lecture.common.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 消息队列配置
 * </p>
 * <br>
 * Creation Time: 2022/4/20 16:35
 *
 * @author zqc
 * @since 1.0
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue() {
        return new Queue(MqConstant.QUEUE_ORDER);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MqConstant.EXCHANGE_ORDER);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(MqConstant.ROUTE_ORDER);
    }

    /**
     * message的序列化、反序列化
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
