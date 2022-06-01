package com.course.service.score;

import java.util.List;

public interface UnstableScoreStrategy extends ScoreStrategy {
    List<Integer> types();

    long validateTimestamp();
}
