package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.dao.UserMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.PointObject;
import com.course.pojo.ScoreRecord;
import com.course.service.score.FillInformationScoreStrategy;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
public class FillInformation {
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserMapper userMapper;

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
