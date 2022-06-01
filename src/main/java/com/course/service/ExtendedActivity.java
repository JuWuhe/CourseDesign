package com.course.service;

import com.course.event.ConvertibleScoreEvent;
import com.course.pojo.PointObject;
import com.course.pojo.ScoreRecord;
import com.course.utils.FileUtils;
import com.course.utils.JsonUtils;
import org.springframework.context.event.EventListener;

import java.util.Map;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
public class ExtendedActivity {

    public void extendedActivity(){
        System.out.println("+++++extendedActivity积分计算方法执行+++++");
    }
}