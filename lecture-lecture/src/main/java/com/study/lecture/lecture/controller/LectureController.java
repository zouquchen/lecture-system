package com.study.lecture.lecture.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureService;
import com.study.lecture.common.service.lecture.LectureUserRecordService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureForAdminInfoVo;

import com.study.lecture.common.vo.LectureForAdminListQueryVo;
import com.study.lecture.common.vo.LectureForUserInfoVo;
import com.study.lecture.common.vo.LectureForUserListQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/13 22:11
 *
 * @author zqc
 * @since 1.0
 */
@Slf4j(topic = "LectureController")
@RestController
@CrossOrigin
@RequestMapping("/lecture")
public class LectureController {

    @Resource
    private LectureService lectureService;

    @Resource
    private LectureUserRecordService lectureUserRecordService;

    /**
     * 列出所有页面结果
     * @return 所有页面结果
     */
    @GetMapping("/listAll")
    public R listAll() {
        List<Lecture> list = lectureService.list(null);
        return R.ok().put("items", list);
    }

    /**
     * 管理员查看的讲座列表信息
     * @param page 当前页
     * @param limit 当前页显示数量
     * @param lectureForAdminListQueryVo 查询条件，设置为非必选，即使不传该参数也可以查询
     * @return 响应类
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @PostMapping("/adminPageList/{page}/{limit}")
    public R lectureForAdminPageList(@PathVariable int page, @PathVariable int limit,
                           @RequestBody(required = false) LectureForAdminListQueryVo lectureForAdminListQueryVo) {
        log.debug("进入adminPageList");
        return lectureService.lectureForAdminPageList(page, limit, lectureForAdminListQueryVo);
    }

    /**
     * 用户（学生）查看的讲座列表信息
     * @param page 当前页
     * @param limit 当前页显示数量
     * @return 响应类
     */
    @PreAuthorize("hasAnyAuthority('admin','manager','student')")
    @PostMapping("/userPageList/{page}/{limit}")
    public R lectureForUserPageList(@PathVariable int page, @PathVariable int limit,
                          @RequestBody(required = false)LectureForUserListQueryVo lectureForUserListQueryVo) {
        return lectureService.lectureForUserPageList(page, limit, lectureForUserListQueryVo);
    }

    /**
     * 用户（学生）查看已经预约过的讲座信息列表
     * @param page 当前页
     * @param limit 当前页显示数量
     * @return 响应类
     */
    @PreAuthorize("hasAnyAuthority('admin','manager','student')")
    @GetMapping("/userRecordPageList/{page}/{limit}")
    public R userRecordPageList(@PathVariable int page, @PathVariable int limit) {
        return lectureUserRecordService.getLectureUserRecordPageList(page, limit);
    }

    /**
     * 添加讲座
     * @param lecture 讲座信息
     * @return 添加成功标志
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @PostMapping("/addLecture")
    public R addLecture(@Validated @RequestBody Lecture lecture) {
        if (lectureService.addLecture(lecture)) {
            return R.ok("添加成功");
        } else {
            throw new GlobalException("添加讲座失败！");
        }
    }

    /**
     * 逻辑删除讲座
     * @param id 讲座id
     * @return 删除成功标志
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @PostMapping("/deleteLecture/{id}")
    public R deleteLecture(@PathVariable Long id) {
        lectureService.deleteLecture(id);
        return R.ok();
    }

    /**
     * 根据id获取讲座详情，用于修改讲座信息
     * @param id 讲座id
     * @return 讲座详情
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @GetMapping("/getLecture/{id}")
    public R getLectureForSaveById(@PathVariable long id) {
        Lecture lecture = lectureService.getLectureById(id);
        return R.ok("获取成功").put("lectureInfo", lecture);
    }

    /**
     * 更新讲座
     * @param lecture 讲座信息
     * @return 更新成功标志
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @PostMapping("/updateLecture")
    public R updateLecture(@Validated @RequestBody Lecture lecture) {
        if (lectureService.updateLecture(lecture)) {
            return R.ok("更新成功");
        } else {
            throw new GlobalException("更新讲座失败！");
        }
    }

    /**
     * 根据id获取讲座详情，显示详情页面 (for user)
     * @param id 讲座id
     * @return 讲座详情
     */
    @PreAuthorize("hasAnyAuthority('admin','manager','student')")
    @GetMapping("/getLectureInfoForUser/{id}")
    public R getLectureInfoForUserById(@PathVariable long id) {
        LectureForUserInfoVo lecture = lectureService.getLectureInfoForUserById(id);
        return R.ok("获取成功").put("lectureInfo", lecture);
    }

    /**
     * 根据id获取讲座详细信息（for admin）
     * @param id 讲座id
     * @return 讲座详情
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @GetMapping("/getLectureInfoForAdmin/{id}")
    public R getLectureInfoForAdminById(@PathVariable long id) {
        LectureForAdminInfoVo lectureInfo = lectureService.getLectureInfoForAdminById(id);
        return R.ok().put("lectureInfo", lectureInfo);
    }

    /**
     * 根据id获取讲座详细信息和用户预约列表，用于讲座详情页面
     * @param id 讲座id
     * @return 讲座详情
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @GetMapping("/getLectureInfoAndUserListForAdminById/{id}")
    public R getLectureInfoAndUserListForAdminById(@PathVariable long id) {
        LectureForAdminInfoVo lectureInfo = lectureService.getLectureInfoForAdminById(id);
        lectureUserRecordService.setOrderedUserListAndData(lectureInfo);
        return R.ok().put("lectureInfo", lectureInfo);
    }

    /**
     * 关闭签到系统（就是将讲座状态从0改为1，结束讲座）
     * @param id 讲座id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('admin','manager')")
    @PostMapping("/finishLectureSignById/{id}")
    public R finishLectureSignById(@PathVariable long id) {
        UpdateWrapper<Lecture> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("state", 1);
        lectureService.update(wrapper);
        return R.ok();
    }
}
