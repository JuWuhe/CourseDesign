package com.course.service.score;

import com.course.dao.BloodSugarRecordMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BloodSugarScoreStrategy implements ScoreStrategy {
    public static final int type = 2;
    @Autowired
    private BloodSugarRecordMapper recordMapper;
    @Override
    public int type() {
        return type;
    }
    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        if (recordMapper.countByUserId(loginUser.getUserId()) > 3) {
            return new ScoreRecord(loginUser.getUserId(), 1, type);
        }
        return null;
    }
}
