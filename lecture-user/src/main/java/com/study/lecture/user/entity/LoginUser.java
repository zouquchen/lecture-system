package com.study.lecture.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 实现UserDetails接口，封装用户信息。
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/5 19:05
 *
 * @author zqc
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 当前用户
     */
    private User user;

    /**
     * 当前用户权限列表
     */
    private List<String> permissions;

    /**
     * Redis默认是不会把它进行序列，但是这样会出问题
     * 其实，我们不需要把这个成员变量序列号存储到Redis当中，我们只需要存储permissions即可。
     * 我们可以将permissions转换为authorities。
     * 通过 @JSONField(serialize = false) 注解，可以不让它序列号。
     */
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        // 把permissions中String类型的权限信息封装成SimpleGrantedAuthority对象
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

