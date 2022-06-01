package com.course;

import com.course.pojo.LoginUser;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

public class TestUtil {

    public static void setUser(LoginUser u){
        USER_CONTEXT.set(u);
    }

    public static void removeUser(){
        USER_CONTEXT.remove();
    }

    public static LoginUser getUser(){
        return USER_CONTEXT.get();
    }

    public static List<Integer> getScoreRecord(JdbcTemplate jdbcTemplate,int userId,int type){
        ArrayList<Integer> counts = new ArrayList<>();
        jdbcTemplate.query("select * from score_record where user_id = ? and type = ?",(rs)->{
            counts.add(rs.getInt("count"));
        },userId,type);
        return counts;
    }
}
