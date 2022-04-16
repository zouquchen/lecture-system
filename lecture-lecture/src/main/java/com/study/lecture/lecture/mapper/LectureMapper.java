package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.lecture.common.entity.lecture.Lecture;
import com.study.lecture.common.vo.LectureForAdminListVo;
import com.study.lecture.common.vo.LectureForAdminInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 讲座发布表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Mapper
public interface LectureMapper extends BaseMapper<Lecture> {

    /**
     * 管理员查看的lecture列表
     * @param page 必须要有Page对象,否则mp无法完成分页查询
     * @return
     */
    List<LectureForAdminListVo> getLectureAdminList(Page<LectureForAdminListVo> page);


    /**
     * 根据id获取lecture详情, 显示详情页面
     * @param id lecture的id
     * @return lecture详情
     */
    LectureForAdminInfoVo getLectureInfoById(Long id);

}
