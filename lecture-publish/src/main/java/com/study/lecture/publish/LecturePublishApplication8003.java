package com.study.lecture.publish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.study.lecture.common", "com.study.lecture.publish"})
public class LecturePublishApplication8003 {

    public static void main(String[] args) {
        SpringApplication.run(LecturePublishApplication8003.class, args);
    }

}
