package com.study.lecture.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zqc
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.study.lecture.common", "com.study.lecture.order"})
public class LectureOrderApplication8004 {

    public static void main(String[] args) {
        SpringApplication.run(LectureOrderApplication8004.class, args);
    }

}
