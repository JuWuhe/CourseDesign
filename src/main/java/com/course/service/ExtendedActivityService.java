package com.course.service;

import com.course.event.ConvertibleScoreEvent;
import com.course.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

//类名与方法名须与controller层拦截的方法一致
@Service
public class ExtendedActivityService {
    @Autowired
    private EventBus EventBus;

    public void extendedActivity(){
        System.out.println("+++++extendedActivity积分计算方法执行+++++");
        EventBus.publishEvent(new ConvertibleScoreEvent(USER_CONTEXT.get(), ConvertibleScoreEvent.ConvertibleScore.ExtendedRecruitment));
    }

}