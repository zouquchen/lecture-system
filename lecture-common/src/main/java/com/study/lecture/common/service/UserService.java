package com.study.lecture.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.entity.LoginUser;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.entity.User;

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
}
