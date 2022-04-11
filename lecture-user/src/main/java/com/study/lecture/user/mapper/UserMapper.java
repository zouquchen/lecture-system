package com.study.lecture.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
