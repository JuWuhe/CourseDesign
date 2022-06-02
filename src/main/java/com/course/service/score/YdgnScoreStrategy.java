package com.course.service.score;

import com.course.dao.ScoreMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class YdgnScoreStrategy implements ScoreStrategy {

    public static final int type = 5;
    @Override
    public int type() {
        return type;
    }
    @Autowired
    private ScoreMapper scoreMapper;
    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        long start = LocalDateTime.now().plusMonths(-3).withSecond(0).withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        return scoreMapper.count(loginUser.getUserId(), type, start, end) == 0 ? new ScoreRecord(loginUser.getUserId(), 2, type) : null;
    }
}
