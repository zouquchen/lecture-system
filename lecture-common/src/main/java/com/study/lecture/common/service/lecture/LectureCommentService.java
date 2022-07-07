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

    /**
     * 根据评论id点赞
     * @param commentId 评论id
     * @return 成功标致
     */
    boolean commentLike(Long commentId);

    /**
     * 根据评论id取消点赞
     * @param commentId 评论id
     * @return 成功标致
     */
    boolean commentCancelLike(Long commentId);

    /**
     * 根据评论id统计点赞数
     * @param commentId 评论id
     * @return 点赞数
     */
    long countCommentLike(Long commentId);

    /**
     * 该用户是否喜欢
     * @param commentId 评论id
     * @return 是否喜欢
     */
    boolean isLike(Long commentId);

    /**
     * 添加评论的消息通知
     * @param commentId 被评论的评论id，通过评论id获取用户id
     * @param replyUsername 回复者的用户名
     * @param content 内容
     */
    void addCommentMessage(Long commentId, String replyUsername, String content);

    /**
     * 添加点赞评论的消息通知
     * @param commentId 被点赞的评论id，通过评论id获取用户id
     * @param likeUsername 点赞者的用户名
     */
    void addLikeMessage(Long commentId, String likeUsername);

}
