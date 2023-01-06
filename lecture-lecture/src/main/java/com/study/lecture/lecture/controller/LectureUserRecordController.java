package com.study.lecture.lecture.controller;

import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.OrderRecordOfOneLectureVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/6/17 14:24
 *
 * @author zqc
 * @since 1.0
 */
@Slf4j(topic = "LectureUserRecordController")
@RestController
@CrossOrigin
@RequestMapping("/lecture/userRecord")
public class LectureUserRecordController {

    @Resource
    public LectureUserRecordService lectureUserRecordService;

    /**
     * 用户讲座签到
     * @param lectureId 讲座id
     * @param username 用户名/账号
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @PostMapping("/userSign/{lectureId}/{username}")
    public R userSign(@PathVariable long lectureId, @PathVariable String username) {
        return lectureUserRecordService.userSign(lectureId, username);
    }

    /**
     * 根据id查询已经预约并签到此讲座的所有用户
     * @param id 讲座id
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @GetMapping("/getSignedUserList/{id}")
    public R getSignedUserList(@PathVariable long id) {
        List<OrderRecordOfOneLectureVo> recordList = lectureUserRecordService.getSignedUserList(id);
        return R.ok().put("recordList", recordList).put("signCount", recordList.size());
    }

    /**
     * 获取用户预约讲座统计信息，预约数、缺席数
     * @return 数据
     */
    @GetMapping("/getDataOfUserRecord")
    public R getDataOfUserRecord() {
        Map<String, Integer> data = lectureUserRecordService.getDataOfUserRecord();
        return R.ok(data);
    }
}
