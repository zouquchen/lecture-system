package com.study.lecture.user;

import com.study.lecture.common.entity.User;
import com.study.lecture.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/5 18:51
 *
 * @author zqc
 * @since 1.0
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void userMapperTest() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
