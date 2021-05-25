package com.wxxy.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    //=================       认证          ========================
    //登录成功跳转页面
    @RequestMapping("/toSuccess")
    public String toSuccess(){
        return "redirect:success";
    }

    @RequestMapping("/success")
    public String success(){
        return "success";
    }
    //登录失败跳转页面
    @RequestMapping("/toError404")
    public String toError404(){
        return "redirect:error404";
    }
    @RequestMapping("/error404")
    public String error404(){
        return "error404";
    }
    //无权限访问
    @RequestMapping("/toError403")
    public String toError403(){
        return "redirect:error403";
    }
    @RequestMapping("/error403")
    public String error403(){
        return "error403";
    }

    //=================       授权          ========================
    @RequestMapping("/hello")
    @ResponseBody
    @Secured({"ROLE_boos","ROLE_student"})//角色判断
    public String hello(){
        return "hello";
    }

    @PreAuthorize("hasAnyAuthority('user','root')")
    @RequestMapping("/root")
    @ResponseBody
    public String root(){
        return "root";
    }

    @RequestMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }
    @RequestMapping("/student")
    @ResponseBody
    public String student(){
        return "student";
    }
    @RequestMapping("/teacher")
    @ResponseBody
    public String teacher(){
        return "teacher";
    }



    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "index";
    }

}
