package com.course.service.score;

import com.course.dao.ScoreMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginScoreStrategy implements ScoreStrategy {
    // TODO: 写
    public static final int type = 0;
    @Override
    public int type() {
        return type;
    }

    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        // TODO：写
        return null;
    }
}
