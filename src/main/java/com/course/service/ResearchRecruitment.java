package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.ConvertibleScoreEvent;
import com.course.event.EventBus;
import com.course.pojo.PointObject;
import com.course.pojo.ScoreRecord;
import com.course.service.score.ConvertibleScoreStrategy;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.util.Map;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
public class ResearchRecruitment {
    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private EventBus EventBus;
    private ConvertibleScoreStrategy convertibleScoreStrategy;
    public void researchRecruitment(){
        System.out.println("+++++researchRecruitment积分计算方法执行+++++");
        EventBus.publishEvent(new ConvertibleScoreEvent(USER_CONTEXT.get(), ConvertibleScoreEvent.ConvertibleScore.ResearchRecruitment));
    }

    @EventListener
    public void ConvertibleScore(ConvertibleScoreEvent event){
        ScoreRecord record = convertibleScoreStrategy.record(event.getLoginUser(), Map.of("score",event.getScore(),"type",event.getTrueType()));
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }
}
