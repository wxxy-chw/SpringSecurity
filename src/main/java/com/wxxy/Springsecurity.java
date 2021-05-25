package com.wxxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.wxxy.mapper")
public class Springsecurity {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Springsecurity.class, args);
        for (String beanDefinitionName : run.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println("master第二次修改");

        System.out.println("hot-fix第二次提交");

        System.out.println("push");
        
        System.out.println("pull");
    }
}
