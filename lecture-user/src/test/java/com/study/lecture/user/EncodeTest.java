package com.study.lecture.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * 密码加密测试
 * </p>
 * <br>
 * Creation Time: 2022/4/5 19:17
 *
 * @author zqc
 * @since 1.0
 */
public class EncodeTest {

    @Test
    void TestBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode("needoffer");
        System.out.println(pass);
        System.out.println(passwordEncoder.matches("needoffer", pass));
    }
}
