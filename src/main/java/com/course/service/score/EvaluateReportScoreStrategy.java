package com.course.service.score;

import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EvaluateReportScoreStrategy implements ScoreStrategy {
    public static final int type = 4;

    @Override
    public int type() {
        return type;
    }
    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        return new ScoreRecord(loginUser.getUserId(), 2, type);
    }
}
