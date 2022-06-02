package com.course.service.score;

import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FillInformationScoreStrategy implements ScoreStrategy {
    public static final int type = 1;

    @Override
    public int type() {
        return type;
    }

    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        Boolean isFirst = (Boolean) context.get("isFirst");
        return isFirst ? new ScoreRecord(loginUser.getUserId(), 2, type()) : null;
    }
}
