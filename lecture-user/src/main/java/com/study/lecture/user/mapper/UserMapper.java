package com.study.lecture.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.lecture.common.entity.user.User;
import com.study.lecture.common.vo.RoleListVo;
import com.study.lecture.common.vo.UserListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 获取所有角色列表
     * @return
     */
    List<RoleListVo> getRoleList();

    /**
     * 根据条件查询用户总数
     * @param username 用户名
     * @param roleId 角色id
     * @return 数量
     */
    int countUserListByCondition(String username, Long roleId);

    /**
     * 根据条件查询用户列表
     * @param begin 起始页
     * @param limit 每页显示数量
     * @param username 用户名
     * @param roleId 角色id
     * @return 列表
     */
    List<UserListVo> getUserListByCondition(int begin, int limit, String username, Long roleId);
}
