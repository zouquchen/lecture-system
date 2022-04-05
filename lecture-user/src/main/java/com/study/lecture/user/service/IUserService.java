package com.study.lecture.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.lecture.common.utils.R;
import com.study.lecture.user.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
public interface IUserService extends IService<User> {

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
}
