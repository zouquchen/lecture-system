package com.study.lecture.lecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author zqc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.study.lecture.common", "com.study.lecture.lecture"})
public class LectureLectureApplication8002 {

    public static void main(String[] args) {
        SpringApplication.run(LectureLectureApplication8002.class, args);
    }

}
