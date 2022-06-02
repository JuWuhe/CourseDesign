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
    public static final int type = 0;
    @Autowired
    ScoreMapper scoreMapper;
    private ConcurrentHashMap<Integer, Long> lastGet = new ConcurrentHashMap<>();

    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        long start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).toEpochSecond(ZoneOffset.ofHours(8));
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        if(loginUser == null) System.out.println("nmsl");
        Long last = lastGet.get(loginUser.getUserId());
        if (last != null) {
            if (last <= end && last >= start) return null;
        }

        int count = scoreMapper.count(loginUser.getUserId(), type, start, end);
        //缓存失效
        lastGet.put(loginUser.getUserId(), now);
        //db内无数据 插入
        if (count == 0) {
            return new ScoreRecord(loginUser.getUserId(), 1, now, type);
        } else {
            return null;
        }
    }
}
