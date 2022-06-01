package com.course.configuration;


import com.course.pojo.LoginUser;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Aspect

public class InterceptorConfig implements WebMvcConfigurer {
    public static final ThreadLocal<LoginUser> USER_CONTEXT = new ThreadLocal<>();

}


