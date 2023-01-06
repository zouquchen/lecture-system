package com.study.lecture.lecture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.constant.MqConstant;
import com.study.lecture.common.constant.RedisConstant;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.entity.lecture.LectureComment;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.service.MqSender;
import com.study.lecture.common.service.lecture.LectureCommentService;
import com.study.lecture.common.service.user.MessageService;
import com.study.lecture.common.vo.CommentVo;
import com.study.lecture.common.vo.MessageMqVo;
import com.study.lecture.lecture.mapper.LectureCommentMapper;
import com.study.lecture.lecture.mapper.LectureMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private MqSender mqSender;

    @Resource
    private LectureMapper lectureMapper;

    @Resource
    private LectureCommentMapper lectureCommentMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @DubboReference(version = "1.0")
    private MessageService messageService;

    /**
     * 根据讲座id获取所有评论
     * @param lectureId 讲座id
     * @return 查询结果
     */
    @Override
    public List<CommentVo> getComment(Long lectureId) {
        List<CommentVo> list = lectureCommentMapper.getCommentVoByLectureId(lectureId);

        // 创建map方便查找
        Map<Long, CommentVo> map = new HashMap<>(list.size());
        // 最终返回的结果
        List<CommentVo> result = new ArrayList<>();
        for (CommentVo vo : list) {
            // 如果是父评论，直接放入result列表
            if (vo.getParentId() == null) {
                result.add(vo);
            }
            // 每个都放入map中，方便查找
            map.put(vo.getId(), vo);

            // 统计喜欢
            vo.setLiked(isLike(vo.getId()));
            vo.setLikes(countCommentLike(vo.getId()));
        }
        try {
            // 子评论放到根评论中
            for (CommentVo vo : list) {
                if (vo.getRootParentId() != null) {
                    CommentVo root = map.get(vo.getRootParentId());
                    vo.setRootParentName(root.getUsername());
                    vo.setParentName(map.get(vo.getParentId()).getUsername());
                    List<CommentVo> children = root.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                        root.setChildren(children);
                    }
                    children.add(vo);
                }
            }
        } catch (Exception e) {
            throw new GlobalException("处理评论数据异常！");
        }

        return result;
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
        String username = loginUser.getUser().getUsername();
        lectureComment.setUserId(userId);
        // 添加
        lectureCommentMapper.insert(lectureComment);

        // 消息通知，发送到消息队列
        MessageMqVo messageMqVo = new MessageMqVo();
        messageMqVo.setCommentId(lectureComment.getParentId());
        messageMqVo.setUsername(username);
        messageMqVo.setContent(lectureComment.getContent());
        messageMqVo.setDate(new Date());

        mqSender.sendMessage(MqConstant.EXCHANGE_MESSAGE_COMMENT, MqConstant.ROUTE_MESSAGE_COMMENT, messageMqVo);
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

    /**
     * 根据评论id点赞
     * @param commentId 评论id
     * @return 成功标致
     */
    @Override
    public boolean commentLike(Long commentId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        String username = loginUser.getUser().getUsername();
        // key: 关键字:评论id
        // value: 用户id
        Long add = redisTemplate.opsForSet().add(RedisConstant.getKeyOfLectureCommentLikes(commentId), userId);
        if (add == null || add == 0) {
            return false;
        }
        // 消息通知，发送到消息队列
        MessageMqVo messageMqVo = new MessageMqVo();
        messageMqVo.setCommentId(commentId);
        messageMqVo.setUsername(username);
        messageMqVo.setDate(new Date());
        mqSender.sendMessage(MqConstant.EXCHANGE_MESSAGE_LIKES, MqConstant.ROUTE_MESSAGE_LIKES, messageMqVo);

        return true;
    }

    /**
     * 根据评论id取消点赞
     * @param commentId 评论id
     * @return 成功标致
     */
    @Override
    public boolean commentCancelLike(Long commentId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        // key: 关键字:评论id
        // value: 用户id
        redisTemplate.opsForSet().remove(RedisConstant.getKeyOfLectureCommentLikes(commentId), userId);
        return true;
    }

    /**
     * 根据评论id统计点赞数
     * @param commentId 评论id
     * @return 点赞数
     */
    @Override
    public long countCommentLike(Long commentId) {
        Long size = redisTemplate.opsForSet().size(RedisConstant.getKeyOfLectureCommentLikes(commentId));
        return size == null ? 0 : size;
    }

    /**
     * 是否喜欢
     * @param commentId 评论id
     * @return 是否喜欢
     */
    @Override
    public boolean isLike(Long commentId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        // key: 关键字:评论id
        // value: 用户id
        Boolean isLike = redisTemplate.opsForSet().isMember(RedisConstant.getKeyOfLectureCommentLikes(commentId), userId);
        if (isLike == null) {
            throw new GlobalException("评论不存在！");
        }
        return isLike;
    }

    /**
     * 添加评论的消息通知
     * @param messageMqVo 消息详情
     */
    @Override
    public void addCommentMessage(MessageMqVo messageMqVo) {
        Long commentId = messageMqVo.getCommentId();
        String replyUsername = messageMqVo.getUsername();
        String content = messageMqVo.getContent();
        Date date = messageMqVo.getDate();

        LectureComment comment = lectureCommentMapper.selectOne(new QueryWrapper<LectureComment>().eq("id", commentId));
        Long userId = comment.getUserId();
        Long lectureId = comment.getLectureId();
        String repliedCommentContent = comment.getContent();

        Lecture lecture = lectureMapper.selectOne(new QueryWrapper<Lecture>().eq("id", lectureId));
        String lectureTitle = lecture.getTitle();

        // 消息内容
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = simpleDateFormat.format(date) + " 【" + lectureTitle + "】" + replyUsername + "回复你的评论【" + repliedCommentContent + "】说：" + content;

        // 添加消息
        messageService.addMessage(userId, lectureId, message);
    }

    /**
     * 添加点赞评论的消息通知
     * @param messageMqVo 消息详情
     */
    @Override
    public void addLikeMessage(MessageMqVo messageMqVo) {
        Long commentId = messageMqVo.getCommentId();
        String likeUsername = messageMqVo.getUsername();
        Date date = messageMqVo.getDate();

        LectureComment comment = lectureCommentMapper.selectOne(new QueryWrapper<LectureComment>().eq("id", commentId));
        Long userId = comment.getUserId();
        Long lectureId = comment.getLectureId();
        String repliedCommentContent = comment.getContent();

        Lecture lecture = lectureMapper.selectOne(new QueryWrapper<Lecture>().eq("id", lectureId));
        String lectureTitle = lecture.getTitle();

        // 消息内容
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = simpleDateFormat.format(date) + " 【" + lectureTitle + "】" + likeUsername + "点赞了你的评论【" + repliedCommentContent + "】";
        // 添加消息
        messageService.addMessage(userId, lectureId, message);
    }
}
