package com.wxxy.config;


import com.wxxy.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启角色权限注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService userDetailsService;
    @Resource
    private DataSource dataSource;

    @Resource
    private PersistentTokenRepository persistentTokenRepository;
    //用户认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //固定用户
        auth.inMemoryAuthentication().passwordEncoder(getPw())
                .withUser("root")//用户名
                .password(new BCryptPasswordEncoder().encode("root"))//密码
                .authorities("root","ROLE_boos");//权限和角色
        //数据库用户
        auth.userDetailsService(userDetailsService).passwordEncoder(getPw());
    }

    //角色和权限访问控制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/index","/error*").permitAll()//放行的请求 所有人都可以访问
                .antMatchers("/css/**","/img/**","/js/**").permitAll()//放行静态资源
                .antMatchers("/admin").hasAuthority("admin")//拥有admin权限才能访问  只能用于单个权限
                .antMatchers("/user").hasAnyAuthority("admin","user","root")//拥有某一个权限才能访问  用于多个权限
                .antMatchers("/teacher").hasRole("teacher")//拥有teacher角色的可以访问
                .antMatchers("/student").hasAnyRole("teacher","student","boos")// 有用某一个角色 就能访问
                .anyRequest().authenticated();//所有请求必须认证才能访问，必须登录
//                .and().csrf().disable();//关闭csrf防护
        http.formLogin()
                .loginPage("/toLogin")//登录页
                .loginProcessingUrl("/login")//登陆访问路径  不需要配置controller
                .successForwardUrl("/toSuccess")//登录成功跳转路径 post请求
//                .successHandler(new MyAuthenticationSuccessHandler("/toSuccess"))//自定义处理器
                .failureForwardUrl("/toError404").permitAll();//登录失败跳转路径 post请求
//                .failureHandler(new MyAuthenticationFailureHandler("/error404.html"));//自定义处理器

        //设置没有访问权限页面
        http.exceptionHandling().accessDeniedPage("/toError403");

        http.rememberMe().tokenRepository(persistentTokenRepository)//数据源
                .tokenValiditySeconds(60)//有效时长
                .userDetailsService(userDetailsService);
        http.logout()
                .logoutSuccessUrl("/toLogin")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
    }

    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);//数据源
//        jdbcTokenRepository.setCreateTableOnStartup(true);//自动建表 第一次启动开启，之后关闭
        return jdbcTokenRepository;
    }
}
