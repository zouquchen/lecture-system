package com.study.lecture.lecture.controller;

import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.LectureForAdminInfoVo;

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
@RestController
@CrossOrigin
@RequestMapping("/lecture")
public class LectureController {

    @Resource
    private LectureService lectureService;


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
     * 分页查询
     * @param page 当前页
     * @param limit 每页显示数量
     * @return 分页查询结果
     */
    @PostMapping("/pageList/{page}/{limit}")
    public R pageList(@PathVariable int page, @PathVariable int limit) {
        return lectureService.pageList(page, limit);
    }

    /**
     * 管理员查看的讲座列表信息
     * @param page 当前页
     * @param limit 当前页显示数量
     * @return 响应类
     */
    @PostMapping("/adminPageList/{page}/{limit}")
    public R adminPageList(@PathVariable int page, @PathVariable int limit) {
        return lectureService.adminPageList(page, limit);
    }

    /**
     * 添加讲座
     * @param lecture 讲座信息
     * @return 添加成功标志
     */
    @PostMapping("/addLecture")
    public R addLecture(@RequestBody Lecture lecture) {
        if (lectureService.addLecture(lecture) >= 1) {
            return R.ok("添加成功");
        } else {
            throw new GlobalException("添加讲座失败！");
        }
    }

    /**
     * 根据id获取讲座详情，用于修改讲座信息
     * @param id 讲座id
     * @return 讲座详情
     */
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
    @PostMapping("/updateLecture")
    public R updateLecture(@RequestBody Lecture lecture) {
        if (lectureService.updateLecture(lecture) >= 1) {
            return R.ok("更新成功");
        } else {
            throw new GlobalException("更新讲座失败！");
        }
    }

    /**
     * 根据id获取讲座详情，显示详情页面
     * @param id 讲座id
     * @return 讲座详情
     */
    @GetMapping("/getLectureInfo/{id}")
    public R getLectureInfo(@PathVariable long id) {
        LectureForAdminInfoVo lecture = lectureService.getLectureInfoById(id);
        return R.ok("获取成功").put("lectureInfo", lecture);
    }
}
