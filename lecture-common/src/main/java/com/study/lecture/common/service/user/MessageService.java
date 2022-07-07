package com.study.lecture.common.service.user;

import com.study.lecture.common.vo.MessageVo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/6 15:11
 *
 * @author zqc
 * @since 1.0
 */
public interface MessageService {
    /**
     * 添加消息到指定用户
     * @param userId 目标用户id
     * @param lectureId 相关讲座id，可以为null
     * @param message 消息内容
     */
    void addMessage(Long userId, Long lectureId, String message);

    /**
     * 根据用户id查看消息数量
     * @return 消息数量
     */
    Long getMessageCount();

    /**
     * 根据用户id获取所有消息
     * @return 结果
     */
    List<MessageVo> getMessage();
}
