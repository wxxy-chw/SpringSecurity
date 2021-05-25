package com.wxxy.service;

import com.wxxy.entity.UserLogin;
import com.wxxy.mapper.UserLoginMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private UserLoginMapper userLoginMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询出用户
        UserLogin user = userLoginMapper.selectUserByName(username);
        //验证
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("user,ROLE_student");
        //返回数据
         return new User(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()),auths);
    }
}
