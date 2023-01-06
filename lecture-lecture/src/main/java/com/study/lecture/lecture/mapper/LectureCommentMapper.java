package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.lecture.LectureComment;
import com.study.lecture.common.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * <p>
 * 讲座评论表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-07-01
 */
@Mapper
public interface LectureCommentMapper extends BaseMapper<LectureComment> {

    /**
     * 根据讲座id获取所有评论信息
     * @param id 讲座id
     * @return 所有信息
     */
    List<CommentVo> getCommentVoByLectureId(Long id);

    /**
     * 根据评论id和用户id删除评论
     * @param commentId 评论id
     * @param userId 用户id
     * @return
     */
    int deleteComment(Long commentId, Long userId);

    /**
     * 根据父评论id删除评论
     * @param parentId 父评论id
     * @return
     */
    int deleteChildrenComment(Long parentId);
}
