package com.course.service;

import com.course.configuration.Skip;
import com.course.dao.ScoreMapper;
import com.course.dao.UserMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.FillInformationScoreStrategy;
import com.course.service.score.LoginScoreStrategy;
import com.course.utils.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginScoreStrategy loginScoreStrategy;

    @Autowired
    private ScoreMapper scoreMapper;
    @Skip
    public LoginUser Login(LoginUser loginUser){
        LoginUser user = userMapper.selectByUsername(loginUser.getUsername());
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) throw new AuthenticationException();
        ScoreRecord scoreRecord = loginScoreStrategy.record(user, Map.of());
        if(scoreRecord != null) scoreMapper.insertRecord(scoreRecord);

        return user;
    }
    @Autowired
    FillInformationScoreStrategy fillInformationScoreStrategy;

    public int fillInformation(String information){
        LoginUser user = USER_CONTEXT.get();
        int i = userMapper.updateInformation(user.getUserId(), information);
        boolean isFirst = user.getInformation() == null;
        user.setInformation(information);
        ScoreRecord scoreRecord = fillInformationScoreStrategy.record(user, Map.of("isFirst", isFirst));
        if(scoreRecord != null) scoreMapper.insertRecord(scoreRecord);
        return i;
    }

}
