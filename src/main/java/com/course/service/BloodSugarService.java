package com.course.service;

import com.course.dao.BloodSugarRecordMapper;
import com.course.service.score.BloodSugarScoreStrategy;
import com.course.dao.ScoreMapper;
import com.course.event.EventBus;
import com.course.pojo.BloodSugarRecord;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
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
public class BloodSugarService {
    @Autowired
    BloodSugarScoreStrategy bloodSugarScoreStrategy;
    @Autowired
    BloodSugarRecordMapper bloodSugarRecordMapper;
    @Autowired
    ScoreMapper scoreMapper;
    public void recordBloodSugar(String record){
        LoginUser user = USER_CONTEXT.get();
        BloodSugarRecord sugarRecord = new BloodSugarRecord(user.getUserId(), record);
        bloodSugarRecordMapper.insertIntoRecord(sugarRecord);
        ScoreRecord scoreRecord = bloodSugarScoreStrategy.record(user, Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(scoreRecord);
    }

}