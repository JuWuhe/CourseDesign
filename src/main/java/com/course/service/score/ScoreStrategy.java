package com.course.service.score;

import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;

import java.util.Map;

public interface ScoreStrategy {
    /**
     * @param loginUser 当前用户
     * @param context   上下文
     * @return 可为空 若为空则不插入
     */
    ScoreRecord record(LoginUser loginUser, Map<?, ?> context);
    int type();

}
