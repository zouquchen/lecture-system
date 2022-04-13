package com.study.lecture.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.user.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单表（权限表）  Mapper 接口
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id获取权限
     * @param userId 用户id
     * @return 用户权限表
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户id获取角色
     * @param userId 用户id
     * @return 用户角色列表
     */
    List<String> selectRoleByUserId(Long userId);
}
