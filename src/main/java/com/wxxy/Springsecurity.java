package com.wxxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.wxxy.mapper")
public class Springsecurity {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurity.class, args);
    }
}
