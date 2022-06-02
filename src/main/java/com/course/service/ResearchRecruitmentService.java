package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.ConvertibleScoreEvent;
import com.course.event.EventBus;
import com.course.pojo.ScoreRecord;
import com.course.service.score.ConvertibleScoreStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
    @Service
public class ResearchRecruitmentService {
    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private EventBus EventBus;
    @Autowired
    private ConvertibleScoreStrategy convertibleScoreStrategy;
    public void researchRecruitment(){
        System.out.println("+++++researchRecruitment积分计算方法执行+++++");
        EventBus.publishEvent(new ConvertibleScoreEvent(USER_CONTEXT.get(), ConvertibleScoreEvent.ConvertibleScore.ResearchRecruitment));
    }
}
