package com.study.lecture.user.controller;

import com.study.lecture.common.service.user.MessageService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/6 23:01
 *
 * @author zqc
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/user/message")
@CrossOrigin
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * 获取系统消息数量
     * @return 响应
     */
    @GetMapping("getMessageCount")
    public R getMessageCount() {
        Long count = messageService.getMessageCount();
        return R.ok().put("messageCount", count);
    }


    @GetMapping("getMessage")
    public R getMessage() {
        List<MessageVo> list = messageService.getMessage();
        return R.ok().put("messageList", list);
    }
}
