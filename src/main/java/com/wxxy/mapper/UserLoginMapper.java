package com.wxxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxxy.entity.UserLogin;

public interface UserLoginMapper extends BaseMapper<UserLogin> {
    UserLogin selectUserByName(String username);
}
