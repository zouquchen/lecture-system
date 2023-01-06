package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.lecture.LectureType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 讲座类型表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Mapper
public interface LectureTypeMapper extends BaseMapper<LectureType> {

}
