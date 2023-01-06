package com.study.lecture.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.lecture.common.constant.MqConstant;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.MqSender;
import com.study.lecture.common.service.lecture.LectureService;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.utils.ResultCodeEnum;
import com.study.lecture.common.vo.LectureForUserListVo;
import com.study.lecture.common.vo.LectureOrderMqVo;
import com.study.lecture.order.service.LectureOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 用户（学生）预约讲座
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/20 15:33
 *
 * @author zqc
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/lectureOrder")
@CrossOrigin
public class LectureOrderController {

    @Resource
    private LectureOrderService lectureOrderService;

    /**
     * <p> 预定讲座，采用秒杀的方式实现 </p>
     * <p> 查询用户是否重复预约，查询讲座剩余场次是否足够，最后将预约信息发送到消息队列</p>
     * @param lectureId 讲座Id
     * @return 响应类
     */
    @PostMapping("/orderLectureById/{lectureId}")
    public R orderLectureById(@PathVariable Long lectureId) {
        return lectureOrderService.orderLectureById(lectureId);
    }

    /**
     * 取消已预约讲座
     * @param lectureId 讲座id
     * @return 响应类
     */
    @PostMapping("/cancelLectureById/{lectureId}")
    public R cancelLectureById(@PathVariable Long lectureId) {
        return lectureOrderService.cancelLectureById(lectureId);
    }

}
