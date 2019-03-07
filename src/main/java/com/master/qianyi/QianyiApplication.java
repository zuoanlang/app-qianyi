package com.master.qianyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.master.qianyi.mapper")
public class QianyiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QianyiApplication.class, args);
    }

}
