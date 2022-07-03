package com.study.lecture.lecture.utils;

import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.vo.CommentVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/7/1 14:30
 *
 * @author zqc
 * @since 1.0
 */
public class CommentUtil {

    /**
     * 根据传入的同级别的评论数据封装成两级关系的评论数据
     * @param list 同级的评论
     * @return 封装成两级关系的评论
     */
    public static List<CommentVo> processComments(List<CommentVo> list) {
        Map<Long, CommentVo> map = new HashMap<>(list.size());
        // 最终返回的结果
        List<CommentVo> result = new ArrayList<>();
        for (CommentVo vo : list) {
            // 如果是父评论，直接放入result列表
            if (vo.getParentId() == null) {
                result.add(vo);
            }
            map.put(vo.getId(), vo);
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
            return result;
        } catch (Exception e) {
            throw new GlobalException("处理评论数据异常！");
        }

    }
}
