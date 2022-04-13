package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.lecture.LectureDataAnalysis;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 讲座数据分析表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Mapper
public interface LectureDataAnalysisMapper extends BaseMapper<LectureDataAnalysis> {

}
