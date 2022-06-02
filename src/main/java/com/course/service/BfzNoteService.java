package com.course.service;

import com.course.dao.BfzMapper;
import com.course.dao.ScoreMapper;
import com.course.pojo.BfzRecord;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.BfzScoreStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

@Service
public class BfzNoteService {
    @Autowired
    private BfzMapper bfzMapper;
    @Autowired
    BfzScoreStrategy bfzScoreStrategy;

    @Autowired
    private  ScoreMapper scoreMapper;
    public void bfzNote(String bfz){
        LoginUser user = USER_CONTEXT.get();
        bfzMapper.insertIntoRecord(new BfzRecord(user.getUserId(), bfz));

        ScoreRecord record = bfzScoreStrategy.record(user, Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }

}
