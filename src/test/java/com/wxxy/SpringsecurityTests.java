package com.wxxy;

import com.wxxy.entity.UserLogin;
import com.wxxy.mapper.UserLoginMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest

class SpringsecurityTests {

    @Resource
    private UserLoginMapper userLoginMapper;
    @Test
    void contextLoads() {
        UserLogin admin = userLoginMapper.selectUserByName("admin");
        System.out.println(admin);
    }
    @DisplayName("加密")
    @Test
    void password(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);
    }

}
