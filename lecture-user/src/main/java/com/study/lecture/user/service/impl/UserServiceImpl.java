package com.study.lecture.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.lecture.common.entity.user.UserRole;
import com.study.lecture.common.exception.GlobalException;
import com.study.lecture.common.utils.AesUtil;
import com.study.lecture.common.utils.JwtUtil;
import com.study.lecture.common.utils.R;
import com.study.lecture.common.entity.user.LoginUser;
import com.study.lecture.common.entity.user.User;
import com.study.lecture.common.vo.*;
import com.study.lecture.user.mapper.UserMapper;
import com.study.lecture.common.service.user.UserService;
import com.study.lecture.user.mapper.UserRoleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zqc
 * @since 2022-04-05
 */
@DubboService(version = "1.0")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录服务
     * @param user 用户
     * @return 响应类
     */
    @Override
    public R login(User user) {
        // 认证的时候需要Authentication对象，所以需要一个Authentication的实现类，这里选择了UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

        // AuthenticationManager authenticate方法进行认证。在SecurityConfig配置类中，我们将AuthenticationManager注入到容器中。
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果认证(登录）通过，authenticate里将包含principal属性，该属性的值就是LoginUser，
        // 如果认证(登录）没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJwt(id);

        // 把完整的用户信息存入redis，userid作为key
        // 设置过期时间为1天
        redisTemplate.opsForValue().set("login:" + id, loginUser, 1, TimeUnit.DAYS);

        // 返回响应类给前端
        return R.ok("登录成功").put("token", jwt);
    }

    /**
     * 获取当前登录用户基本信息
     * @return 响应信息
     */
    @Override
    public R info() {
        // 因为用户已经登录，可以从SecurityContextHolder获取该用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return R.ok()
                .put("name", loginUser.getUsername())
                .put("roles", loginUser.getPermissions())
                .put("avatar", loginUser.getUser().getAvatar());
    }

    /**
     * 登出服务
     * @return
     */
    @Override
    public R logout() {
        // 获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();

        // 删除redis当中的值
        redisTemplate.delete("login:" + id);

        return R.ok("注销成功");
    }

    /**
     * 根据用户id，从redis内获取用户信息
     * @param key 用户key (格式为 login:用户id）
     * @return 用户信息
     */
    @Override
    public LoginUser getUserFromRedisById(String key) {
        return (LoginUser) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取角色列表
     * @return
     */
    @Override
    public R getRoleList() {
        List<RoleListVo> roleList = userMapper.getRoleList();
        return R.ok().put("roleList", roleList);
    }


    /**
     * 根据条件对用户进行分页查询
     * @param page 当前页
     * @param limit 当前页数量
     * @param userListQueryVo 查询条件
     * @return 查询结果
     */
    @Override
    public R getUserPageList(int page, int limit, UserListQueryVo userListQueryVo) {
        String username = userListQueryVo.getUsername();
        Long roleId = userListQueryVo.getRoleId();

        if (username != null) {
            username = "%" + username + '%';
        }

        // 计算begin
        int begin = (page - 1) * limit;

        // 处理数据
        int total = userMapper.countUserListByCondition(username, roleId);
        List<UserListVo> records = userMapper.getUserListByCondition(begin, limit, username, roleId);

        return R.ok().put("total", total).put("records", records);
    }

    /**
     * 获取已登录用户详细信息
     * @return 详细信息
     */
    @Override
    public User getUserInfo() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        BeanUtils.copyProperties(loginUser.getUser(), user);
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateUserInfo(User user) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = loginUser.getUser().getId();
        userMapper.updateUser(id, user.getUsername(), user.getEmail(), user.getPhoneNumber(), user.getSex(), user.getAvatar());

        // 更新loginUser
        loginUser.getUser().setUsername(user.getUsername());
        loginUser.getUser().setEmail(user.getEmail());
        loginUser.getUser().setPhoneNumber(user.getPhoneNumber());
        loginUser.getUser().setSex(user.getSex());
        loginUser.getUser().setAvatar(user.getAvatar());

        // 更新redis
        redisTemplate.opsForValue().set("login:" + id, loginUser, 1, TimeUnit.DAYS);

    }

    /**
     * 添加新用户
     * @param userVo 新用户信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addUser(UserVo userVo) {
        // 密码编码，数据库中的密码非明文密码，是加密后的密码，放在数据库被盗，密码泄露
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        // 前端加密密码转为明文密码
        user.setPassword(AesUtil.desEncrypt(user.getPassword()));
        // 明文密码转为数据库加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        Long userId = user.getId();
        Long roleId = userVo.getRole();
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleMapper.insert(userRole);
    }

    /**
     * 逻辑删除用户
     * @param id 用户id
     */
    @Override
    public void deleteUserById(Long id) {
        userMapper.logicallyDeleteUser(id);
    }

    /**
     * 修改密码
     * @param passwordVo 密码
     */
    @Override
    public void updatePassword(PasswordVo passwordVo) {
        // 数据库密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 获取登录用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();

        String oldPassword = passwordVo.getOldPassword();
        String newPassword = passwordVo.getNewPassword();
        String newCheckPass = passwordVo.getNewCheckPass();

        if (!newPassword.equals(newCheckPass)) {
            throw new GlobalException("两次密码不一致，修改密码失败！");
        }

        // 对前端传入的密码进行解密
        oldPassword = AesUtil.desEncrypt(oldPassword);
        newPassword = AesUtil.desEncrypt(newPassword);

        // 查询并判断原密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);
        String encodedPass = user.getPassword();
        if (!passwordEncoder.matches(oldPassword, encodedPass)) {
            throw new GlobalException("原密码输入错误！");
        }

        userMapper.updatePassword(userId, passwordEncoder.encode(newPassword));
    }

}
