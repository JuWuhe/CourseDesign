package com.course.event;

import com.course.pojo.LoginUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public abstract class BaseScoreEvent {

    protected final Long timeStamp;
    protected final LoginUser loginUser;

    public BaseScoreEvent(LoginUser loginUser) {
        this.timeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        this.loginUser = loginUser;
    }
}
