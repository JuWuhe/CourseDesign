package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.ConvertibleScoreEvent;
import com.course.pojo.ScoreRecord;
import com.course.service.score.ConvertibleScoreStrategy;
import com.course.service.score.ScoreStrategy;
import com.course.service.score.UnstableScoreStrategy;
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

    public ScoreService(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    @EventListener
    public void ConvertibleScore(ConvertibleScoreEvent event){
        ScoreRecord record = convertibleScoreStrategy.record(event.getLoginUser(), Map.of("score",event.getScore(),"type",event.getTrueType()));
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }
}
