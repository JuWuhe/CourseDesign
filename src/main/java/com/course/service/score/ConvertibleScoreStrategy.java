package com.course.service.score;

import com.course.event.ConvertibleScoreEvent;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConvertibleScoreStrategy implements UnstableScoreStrategy {
    private final List<Integer> types;

    public ConvertibleScoreStrategy() {
        types = Arrays.stream(ConvertibleScoreEvent.ConvertibleScore.values()).map(ConvertibleScoreEvent.ConvertibleScore::getTrueType).collect(Collectors.toList());
    }


    @Override
    public int type() {
        return 6;
    }

    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        // TODO: 写
        return null;
    }

    @Override
    public long validateTimestamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)) - LocalDateTime.now().minusYears(-1).toEpochSecond(ZoneOffset.ofHours(8));
    }

    @Override
    public List<Integer> types() {
        return types;
    }
}
