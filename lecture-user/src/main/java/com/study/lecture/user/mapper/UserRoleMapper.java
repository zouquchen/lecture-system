package com.study.lecture.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.user.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单表（权限表）  Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
