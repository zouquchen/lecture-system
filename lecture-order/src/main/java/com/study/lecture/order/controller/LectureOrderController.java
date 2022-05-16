package com.study.lecture.order.controller;

import com.study.lecture.common.constant.MqConstant;
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
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RestController
@RequestMapping("/lectureOrder")
@CrossOrigin
public class LectureOrderController implements InitializingBean {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MqSender mqSender;

    @DubboReference(version = "1.0")
    private LectureService lectureService;

    /**
     * <p> 预定讲座，采用秒杀的方式实现 </p>
     * <p> 查询用户是否重复预约，查询讲座剩余场次是否足够，最后将预约信息发送到消息队列</p>
     * @param lectureId 讲座Id
     * @return
     */
    @PostMapping("/orderLectureById/{lectureId}")
    public R orderLectureById(@PathVariable Long lectureId) {
        // 获取用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        // 从redis内查询该用户是否重复预定讲座
        LectureUserRecord lectureUserRecord = (LectureUserRecord) redisTemplate.opsForValue().get("lecture:" + userId + ":" + lectureId);
        if (lectureUserRecord != null) {
            throw new GlobalException(ResultCodeEnum.REPEAT_ORDER.getMessage(), ResultCodeEnum.REPEAT_ORDER.getCode());
        }

        // 递减redis内该讲座剩余可预约数量
        // stock为递减之后的库存，decrement是原子操作
        Long stock = redisTemplate.opsForValue().decrement("lectureStock:" + lectureId);
        // 查询不到讲座库存，说明讲座不存在
        if (stock == null) {
            throw new GlobalException(ResultCodeEnum.LECTURE_NOT_EXIT.getMessage(), ResultCodeEnum.LECTURE_NOT_EXIT.getCode());
        }
        if (stock < 0) {
            // 恢复到0
            redisTemplate.opsForValue().increment("lectureStock:" + lectureId);
            throw new GlobalException(ResultCodeEnum.EMPTY_STORE.getMessage(), ResultCodeEnum.EMPTY_STORE.getCode());
        }

        // 发送到消息队列，由消息队列异步处理：在数据库中创建用户预约讲座的记录
        LectureOrderMqVo lectureOrderMqVo = new LectureOrderMqVo(userId, lectureId);
        // TODO 发送到消息队列是否成功
        boolean b = mqSender.sendMessage(MqConstant.EXCHANGE_ORDER, MqConstant.ROUTE_ORDER, lectureOrderMqVo);

        return R.ok();
    }

    /**
     * 初始化时执行的方法
     * 把讲座的可预约数量加载到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<LectureForUserListVo> list = lectureService.lectureForUserList();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(vo -> {
            // 设置讲座的剩余预约数量
            redisTemplate.opsForValue().set("lectureStock:" + vo.getId(), vo.getStore());
        });
    }
}
