package com.study.lecture.user.service.impl;

import com.study.lecture.common.constant.RedisConstant;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.service.user.MessageService;
import com.study.lecture.common.vo.MessageVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/6 15:13
 *
 * @author zqc
 * @since 1.0
 */
@DubboService(version = "1.0")
public class MessageServiceImpl implements MessageService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加消息到指定用户
     * @param userId 目标用户id
     * @param lectureId 相关讲座id，可以为null
     * @param message 消息内容
     */
    @Override
    public void addMessage(Long userId, Long lectureId, String message) {
        MessageVo messageVo = new MessageVo();
        messageVo.setUserId(userId);
        messageVo.setLectureId(lectureId);
        messageVo.setMessage(message);

        // 添加消息通知到redis
        redisTemplate.opsForList().rightPush(RedisConstant.getKeyOfSystemMessage(userId), messageVo);
    }

    /**
     * 根据用户id查看消息数量
     * @return 消息数量
     */
    @Override
    public Long getMessageCount() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        return redisTemplate.opsForList().size(RedisConstant.getKeyOfSystemMessage(userId));
    }


    /**
     * 根据用户id获取所有消息
     * @return 结果
     */
    @Override
    public List<MessageVo> getMessage() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        List<MessageVo> list = new ArrayList<>();
        Long size = redisTemplate.opsForList().size(RedisConstant.getKeyOfSystemMessage(userId));

        // 无消息返回空列表
        if (size == null) {
            return list;
        }
        for (int i = 0; i < size; i++) {
            list.add((MessageVo) redisTemplate.opsForList().leftPop(RedisConstant.getKeyOfSystemMessage(userId)));
        }
        return list;
    }
}
