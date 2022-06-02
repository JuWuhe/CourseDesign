package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.ConvertibleScoreEvent;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.ConvertibleScoreStrategy;
import com.course.service.score.ScoreStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    @Autowired
    ConvertibleScoreStrategy convertibleScoreStrategy;
    private final ScoreMapper scoreMapper;

    public ScoreService(List<ScoreStrategy> scoreStrategies,ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
        for(ScoreStrategy strategy : scoreStrategies){
            if(strategy instanceof ConvertibleScoreStrategy){
                duihuanScoreTypes.addAll(((ConvertibleScoreStrategy)strategy).getTypes());
            }else {
                chengzhangScoreTypes.add(strategy.type());
            }
        }
    }

    @EventListener
    public void ConvertibleScore(ConvertibleScoreEvent event){
        ScoreRecord record = convertibleScoreStrategy.record(event.getLoginUser(), Map.of("score",event.getScore(),"type",event.getTrueType()));
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }
    private final List<Integer> chengzhangScoreTypes = new ArrayList<>();
    public char calChengZhangeScoreByMonth(LoginUser loginUser){
        long start = LocalDateTime.now().withDayOfMonth(1).withSecond(0).withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        int sum = scoreMapper.sum(loginUser.getUserId(), start, end, chengzhangScoreTypes);
        if (sum<=10) return 'C';
        if (sum<=25) return 'B';
        return 'A';
    }

    private final List<Integer> duihuanScoreTypes = new ArrayList<>();
    public int calDuihuanScore(LoginUser loginUser){
        long start = LocalDateTime.now().minusYears(1).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        return scoreMapper.sum(loginUser.getUserId(), start, end,duihuanScoreTypes);
    }
}
