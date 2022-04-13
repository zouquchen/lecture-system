package com.study.lecture.lecture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.lecture.LectureUserRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 讲座预约记录表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-13
 */
@Mapper
public interface LectureUserRecordMapper extends BaseMapper<LectureUserRecord> {

}
