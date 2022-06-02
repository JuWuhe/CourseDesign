package com.course.service;

import com.course.event.ConvertibleScoreEvent;
import com.course.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

@Service
public class FollowUpService {
    @Autowired
    private EventBus EventBus;

    public void followUp(){
        System.out.println("+++++followUp积分计算方法执行+++++");
        EventBus.publishEvent(new ConvertibleScoreEvent(USER_CONTEXT.get(), ConvertibleScoreEvent.ConvertibleScore.FollowUp));
    }

}
