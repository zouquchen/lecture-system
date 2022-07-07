package com.study.lecture.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p> 启动类 </p>
 *
 * @author zqc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.study.lecture.common", "com.study.lecture.user"})
public class LectureUserApplication8001 {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LectureUserApplication8001.class, args);
    }

}
