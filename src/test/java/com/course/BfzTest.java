package com.course;

import com.course.pojo.LoginUser;
import com.course.service.BfzNoteService;
import com.course.service.UserService;
import com.course.service.score.BfzScoreStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.course.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class BfzTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BfzNoteService noteService;
    @Autowired
    UserService userService;

    @Test
    @Rollback
    public void bfzTest(){
        LoginUser user = creatTestUser();
        setUser(user);

        noteService.bfzNote("bfz");
        noteService.bfzNote("bfz");
        noteService.bfzNote("bfz");

        int i = countNote();
        assertEquals(3,i,"应该存在三个记录");

        List<Integer> list = getScoreRecord(jdbcTemplate, user.getUserId(), BfzScoreStrategy.type);
        assertEquals(1, list.size(), "此时应有只有一条记录");

        assertEquals(3,list.get(0),"并发症记录一般每年填写 1 次，每年只计分 1 次 一次3分");
    }

    private int countNote(){
        LoginUser loginUser = getUser();
        AtomicInteger integer = new AtomicInteger();
        jdbcTemplate.query("select count(*) as c from bfz_record where user_id = ?;", (rs) -> {
            integer.set(rs.getInt("c"));
        },loginUser.getUserId());
        return integer.get();
    }


    private LoginUser creatTestUser() {
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        return userService.Login(new LoginUser("test_username", "p1"));
    }

}
