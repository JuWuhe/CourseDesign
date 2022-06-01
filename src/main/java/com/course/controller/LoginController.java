package com.course.controller;

import com.course.configuration.Skip;
import com.course.pojo.LoginUser;
import com.course.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author lixuy
 * Created on 2019-04-11
 */

//登录平台
@RestController
@RequestMapping("/user")
public class LoginController {
    UserService userService;

    /**
     * 采用构造器注入 保证脱离IOC注入器仍可以使用
     * @param userService
     */
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login",consumes = "application/json")
    @Skip
    public String login(@RequestBody @Validated LoginUser loginUser, HttpSession httpSession){
        if (httpSession.getAttribute(LoginUser.SESSION_KEY) != null) {
            return "已登录";
        }
        httpSession.setAttribute(LoginUser.SESSION_KEY, userService.Login(loginUser));
        return "登录成功";
    }

    @GetMapping("/test")
    public String test(){
        return "d";
    }
}
