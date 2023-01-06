package com.study.lecture.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 不加载redis的配置
 * @author zqc
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.study.lecture.common", "com.study.lecture.oss"})
public class LectureOssApplication8003 {

    public static void main(String[] args) {
        SpringApplication.run(LectureOssApplication8003.class, args);
    }

}
