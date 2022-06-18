package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureUserRecordVo;
import com.study.lecture.lecture.mapper.LectureUserRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲座预约记录表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@DubboService(version = "1.0")
public class LectureUserRecordServiceImpl extends ServiceImpl<LectureUserRecordMapper, LectureUserRecord> implements LectureUserRecordService {

    @Resource
    private LectureUserRecordMapper lectureUserRecordMapper;

    /**
     * 获取用户预约讲座记录表
     * @param page 当前页
     * @param limit 每页数据个数
     * @return 记录
     */
    @Override
    public R getLectureUserRecordPageList(int page, int limit) {
        // 获取已登录用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        // 查询数据
        int begin = limit * (page - 1);
        List<LectureUserRecordVo> records = lectureUserRecordMapper.getLectureUserRecordPageList(userId, begin, limit);
        for(LectureUserRecordVo vo : records) {

            // 前端显示的状态：未开始、已打卡、未参加
            if (vo.getState() == 0) {
                // 如果讲座状态state = 0 表示已发布，那么打卡状态显示“未开始”
                vo.setSignState("未开始");
            } else if (vo.getState() == 1 && vo.getSignTime() == null) {
                // 如果讲座状态state = 1 表示结束，并且未到，那么打卡状态显示“未参加”
                vo.setSignState("未参加");
            } else if (vo.getState() == 1 && vo.getSignTime() != null) {
                // 如果讲座状态state = 1 表示结束，并且已签到，那么打卡状态显示“已打卡”
                vo.setSignState("已打卡");
            } else {
                vo.setSignState("状态错误！！");
            }

        }
        int total = lectureUserRecordMapper.countLectureUserRecord(userId);

        return R.ok().put("records", records).put("total", total);
    }

    /**
     * 根据讲座id和用户id删除用户预约讲座记录
     * @param lectureId 讲座id
     * @param userId 用户id
     */
    @Override
    public void deleteLectureUserRecord(Long lectureId, Long userId) {
        lectureUserRecordMapper.deleteLectureUserRecord(lectureId, userId);
    }
}