package com.course;

import com.course.pojo.LoginUser;
import com.course.service.UserService;
import com.course.service.YdgnNoteService;
import com.course.service.score.YdgnScoreStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.course.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class YdgnTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    LoginUser loginUser;
    @Autowired
    YdgnNoteService ydgnNoteService;


    @BeforeEach
    public void initEachUser(){
        //https://stackoverflow.com/questions/58657587/beforeall-and-transaction-are-not-working-changes-on-db-side-are-not-rollbac
        if (loginUser == null) {
            loginUser = creatTestUser();
        }
        setUser(loginUser);
    }

    @Test
    public void ydgnTest(){
        ydgnNoteService.ydgnNote();
        List<Integer> list = getScoreRecord(jdbcTemplate, getUser().getUserId(), YdgnScoreStrategy.type);
        assertEquals(1, list.size(), "---此时应只有一条记录---");
        assertEquals(2,list.get(0),"胰岛功能3个月只积分1次2分");

        ydgnNoteService.ydgnNote();
        ydgnNoteService.ydgnNote();
        ydgnNoteService.ydgnNote();
        ydgnNoteService.ydgnNote();
        assertEquals(1, list.size(), "---此时应只有一条记录---");
        assertEquals(2,list.get(0),"胰岛功能3个月只积分1次2分");
    }

    private LoginUser creatTestUser() {
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        return userService.Login(new LoginUser("test_username", "p1"));
    }


}
