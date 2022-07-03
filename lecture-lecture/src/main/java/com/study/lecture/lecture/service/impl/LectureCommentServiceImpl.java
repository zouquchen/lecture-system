package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.lecture.LectureComment;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.lecture.LectureCommentService;
import com.study.lecture.common.vo.CommentVo;
import com.study.lecture.lecture.mapper.LectureCommentMapper;
import com.study.lecture.lecture.utils.CommentUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 讲座评论表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@DubboService
public class LectureCommentServiceImpl extends ServiceImpl<LectureCommentMapper, LectureComment> implements LectureCommentService {

    @Resource
    private LectureCommentMapper lectureCommentMapper;

    /**
     * 根据讲座id获取所有评论
     * @param lectureId 讲座id
     * @return 查询结果
     */
    @Override
    public List<CommentVo> getComment(Long lectureId) {
        List<CommentVo> commentVoList = lectureCommentMapper.getCommentVoByLectureId(lectureId);
        return CommentUtil.processComments(commentVoList);
    }

    /**
     * 根据讲座id或父评论id添加评论
     * @param lectureComment 评论
     */
    @Override
    public void addComment(LectureComment lectureComment) {
        // 获取评论者身份
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        lectureComment.setUserId(userId);
        // 添加
        lectureCommentMapper.insert(lectureComment);
    }

    /**
     * 根据评论id删除评论
     * @param commentId 评论id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteComment(Long commentId) {
        // 获取评论者身份
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        int i = lectureCommentMapper.deleteComment(commentId, userId);
        if (i <= 0) {
            throw new GlobalException("删除评论失败");
        }
        // 删除子评论
        lectureCommentMapper.deleteChildrenComment(commentId);
    }

    /**
     * 根据评论id更新评论
     * @param lectureComment 评论
     */
    @Override
    public void updateComment(LectureComment lectureComment) {
        // TODO 核对修改者身份
        UpdateWrapper<LectureComment> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", lectureComment.getId());
        lectureCommentMapper.update(lectureComment,wrapper);
    }
}
