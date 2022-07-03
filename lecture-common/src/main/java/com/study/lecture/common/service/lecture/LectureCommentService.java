package com.study.lecture.common.service.lecture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.lecture.LectureComment;
import com.study.lecture.common.vo.CommentVo;

import java.util.List;

/**
 * <p>
 * 讲座评论表 服务类
 * </p>
 *
 * @author zqc
 * @since 2022-07-01
 */
public interface LectureCommentService extends IService<LectureComment> {

    /**
     * 根据讲座id获取所有评论
     * @param lectureId 讲座id
     * @return 查询结果
     */
    List<CommentVo> getComment(Long lectureId);

    /**
     * 根据讲座id或父评论id添加评论
     * @param lectureComment 讲座信息
     */
    void addComment(LectureComment lectureComment);

    /**
     * 根据评论id删除评论
     * @param commentId 评论id
     */
    void deleteComment(Long commentId);

    /**
     * 根据评论id更新评论
     * @param lectureComment 讲座信息
     */
    void updateComment(LectureComment lectureComment);
}
