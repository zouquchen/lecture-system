package com.study.lecture.common.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.entity.user.User;
import com.study.lecture.common.vo.UserListQueryVo;
import com.study.lecture.common.vo.UserListVo;
import com.study.lecture.common.vo.UserVo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
public interface UserService extends IService<User> {

    /**
     * 登录服务
     * @param user 用户
     * @return 响应信息
     */
    R login(User user);

    /**
     * 获取当前登录用户基本信息
     * @return 响应信息
     */
    R info();

    /**
     * 登出服务
     * @return 响应信息
     */
    R logout();

    /**
     * 根据用户id，从redis内获取用户信息
     * @param key 用户key (格式为 login:用户id）
     * @return 用户信息
     */
    LoginUser getUserFromRedisById(String key);

    /**
     * 获取所有角色列表
     * @return 响应类
     */
    R getRoleList();

    /**
     * 根据条件对用户进行分页查询
     * @param page 当前页
     * @param limit 当前页数量
     * @param userListQueryVo 查询条件
     * @return 查询结果
     */
    R getUserPageList(int page, int limit, UserListQueryVo userListQueryVo);

    /**
     * 获取已登录用户详细信息信息
     * @return 用户信息
     */
    User getUserInfo();

    /**
     * 更新用户详细信息信息
     * @param user 用户
     */
    void updateUserInfo(User user);

    /**
     * 添加新用户
     * @param userVo 新用户信息
     */
    void addUser(UserVo userVo);

    /**
     * 逻辑删除用户
     * @param id 用户id
     */
    void deleteUserById(Long id);
}
