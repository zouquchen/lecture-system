package com.study.lecture.publish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zqc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.study.lecture.common", "com.study.lecture.publish"})
public class LecturePublishApplication8004 {

    public static void main(String[] args) {
        SpringApplication.run(LecturePublishApplication8004.class, args);
    }

}
