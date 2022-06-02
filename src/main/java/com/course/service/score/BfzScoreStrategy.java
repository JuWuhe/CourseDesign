package com.course.service.score;

import com.course.dao.BfzMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class BfzScoreStrategy implements ScoreStrategy {
    public static final int type = 3;

    @Autowired
    private BfzMapper bfzMapper;
    @Override
    public int type() {
        return type;
    }
    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        long start = LocalDateTime.now().withDayOfYear(1).withSecond(0).withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59).toEpochSecond(ZoneOffset.ofHours(8));
        int count = bfzMapper.countByUserId(loginUser.getUserId(), start, end);
        return count > 1 ? null : new ScoreRecord(loginUser.getUserId(), 3, type);
    }
}
