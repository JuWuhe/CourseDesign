package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.ConvertibleScoreEvent;
import com.course.event.EventBus;
import com.course.service.score.ConvertibleScoreStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

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
