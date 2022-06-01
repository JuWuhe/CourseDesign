package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.pojo.ScoreRecord;
import com.course.service.score.YdgnScoreStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import static com.course.configuration.InterceptorConfig.USER_CONTEXT;
/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
    @Service
public class YdgnNoteService {
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    YdgnScoreStrategy ydgnScoreStrategy;
    public void ydgnNote(){
        System.out.println("+++++ydgnNote积分计算方法执行+++++");
        ScoreRecord record = ydgnScoreStrategy.record(USER_CONTEXT.get(), Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }

}
