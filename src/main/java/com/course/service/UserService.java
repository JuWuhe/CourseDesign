package com.course.service;

import com.course.configuration.Skip;
import com.course.dao.ScoreMapper;
import com.course.dao.UserMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.LoginScoreStrategy;
import com.course.utils.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    LoginScoreStrategy loginScoreStrategy;

    @Autowired
    private ScoreMapper scoreMapper;
    @Skip
    public LoginUser Login(LoginUser loginUser){
        LoginUser user = userMapper.selectByUsername(loginUser.getUsername());
        if (user == null || !((LoginUser) user).getPassword().equals(loginUser.getPassword())) throw new AuthenticationException();
        ScoreRecord scoreRecord = loginScoreStrategy.record(USER_CONTEXT.get(), Map.of());
        //if(scoreRecord != null) ;
        scoreMapper.insertRecord(scoreRecord);

        return user;
    }

}
