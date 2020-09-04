package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.study.mapper")
public class Mmall004Application {

    public static void main(String[] args) {
        SpringApplication.run(Mmall004Application.class, args);
    }

}
