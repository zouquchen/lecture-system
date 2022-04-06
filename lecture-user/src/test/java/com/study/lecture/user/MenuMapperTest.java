package com.study.lecture.user;

import com.study.lecture.user.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 *
 * </p>
 * <br>
 * Creation Time: 2022/4/6 10:58
 *
 * @author zqc
 * @since 1.0
 */
@SpringBootTest
public class MenuMapperTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void menuMapperTest() {
        System.out.println(menuMapper.selectRoleByUserId(1L));
    }

}
