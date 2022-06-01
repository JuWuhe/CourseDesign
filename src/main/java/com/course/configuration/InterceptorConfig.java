package com.course.configuration;

import com.course.dao.ScoreMapper;
import com.course.event.AccessScoreEvent;
import com.course.event.EventBus;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.LoginScoreStrategy;
import com.course.utils.AuthenticationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Configuration
@Aspect

public class InterceptorConfig implements WebMvcConfigurer {
    public static final ThreadLocal<LoginUser> USER_CONTEXT = new ThreadLocal<>();
    @Autowired
    UserInterceptor userInterceptor;

    LoginScoreStrategy loginScoreStrategy;
    @Autowired
    private ScoreMapper scoreMapper;

    @Around(value = "execution(* com.course.service.*.*(..)) && !@annotation(Skip) && !@annotation(org.springframework.context.event.EventListener)")
    public Object InterceptMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (USER_CONTEXT.get() == null){
            throw new AuthenticationException();
        }
        ScoreRecord scoreRecord = loginScoreStrategy.record(USER_CONTEXT.get(), Map.of());
        if(scoreRecord != null) scoreMapper.insertRecord(scoreRecord);
        Object proceed = proceedingJoinPoint.proceed();
        return proceed;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/**");

    }


    @Component
    static class UserInterceptor implements HandlerInterceptor {
        LoginScoreStrategy loginScoreStrategy;
        @Autowired
        private ScoreMapper scoreMapper;
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (!(handler instanceof HandlerMethod)) return true;
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.getAnnotation(Skip.class) != null) {
                return true;
            }
            LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.SESSION_KEY);
            if (user == null){
                throw new AuthenticationException();
            }
            ScoreRecord scoreRecord = loginScoreStrategy.record(USER_CONTEXT.get(), Map.of());
            if(scoreRecord != null) scoreMapper.insertRecord(scoreRecord);
            USER_CONTEXT.set(user);
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            USER_CONTEXT.remove();
        }
    }


}

