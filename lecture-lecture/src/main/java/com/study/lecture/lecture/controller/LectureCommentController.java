package com.study.lecture.lecture.controller;

import com.study.lecture.common.entity.lecture.LectureComment;
import com.study.lecture.common.service.lecture.LectureCommentService;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j(topic = "LectureCommentController")
@RestController
@CrossOrigin
@RequestMapping("/lecture/comment")
public class LectureCommentController {

    @Resource
    private LectureCommentService lectureCommentService;

    /**
     * 根据讲座id查询讲座的所有评论
     * @param lectureId 讲座id
     * @return 查询结果
     */
    @GetMapping("getComment/{lectureId}")
    public R getComment(@PathVariable Long lectureId) {
        List<CommentVo> list = lectureCommentService.getComment(lectureId);
        return R.ok().put("list", list);
    }

    /**
     * 针对于讲座或评论发布评论
     * @param lectureComment 评论
     * @return 结果
     */
    @PostMapping("addComment")
    public R addComment(@RequestBody LectureComment lectureComment) {
        lectureCommentService.addComment(lectureComment);
        return R.ok();
    }


    /**
     * 删除评论
     * @param commentId 评论id
     * @return 结果
     */
    @PostMapping("deleteComment/{commentId}")
    public R deleteComment(@PathVariable Long commentId) {
        lectureCommentService.deleteComment(commentId);
        return R.ok();
    }



    /**
     * 编辑修改评论
     * @param lectureComment 评论
     * @return 结果
     */
    @PostMapping("updateComment}")
    public R updateComment(@RequestBody LectureComment lectureComment) {
        lectureCommentService.updateComment(lectureComment);
        return R.ok();
    }
}
