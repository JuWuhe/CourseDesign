package com.course;


import com.course.dao.UserMapper;
import com.course.pojo.LoginUser;
import com.course.service.UserService;
import com.course.utils.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.course.TestUtil.getScoreRecord;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class LoginTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @BeforeEach
    public void flushDB(){
        jdbcTemplate.update("delete from score_record");
    }



    @Test
    @Rollback
    public void testLoginMapper(){
        //language=sql
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        assertEquals(userMapper.selectByUsername("test_username").getPassword(), "p1");
        assertNull(userMapper.selectByUsername(""));
    }


    @Test
    @Rollback
    public void testLoginScore(){
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        //错误的账号 || 错误的密码 应该抛出异常
        assertThrows(AuthenticationException.class, () -> userService.Login(new LoginUser("test_username","p2")));
        assertThrows(AuthenticationException.class, () -> userService.Login(new LoginUser("5646789","p1")));

        LoginUser user  = userService.Login(new LoginUser("test_username", "p1"));
        user = userService.Login(user);
        List<Integer> counts = getScoreRecord(jdbcTemplate, user.getUserId(), 0);
        assertEquals(1, counts.size(),"重复登录应该只存在一个记录");
        assertEquals(1,counts.get(0) , "当日登录只加一分");
    }

    @Test
    @Rollback
    public void testFillInformationScore(){
        LoginUser user = creatTestUser();
        TestUtil.setUser(user);

        String fill = "testForFillInformation";
        userService.fillInformation(fill);
        assertEquals(fill,user.getInformation(),"当前用户应已经填写资料");

        jdbcTemplate.query("select information from login_user where user_id = ?",(rs)->{
            assertEquals(fill,rs.getString("information"), "数据库中的用户应被修改填充");
        },user.getUserId());


        userService.fillInformation(fill);
        List<Integer> counts = getScoreRecord(jdbcTemplate, user.getUserId(), 1);
        assertEquals(1, counts.size(),"重复填充应该只存在一个记录");
        assertEquals(2,counts.get(0) , "填充只加两分");

        TestUtil.removeUser();
    }

    private LoginUser creatTestUser() {
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        LoginUser user  = userService.Login(new LoginUser("test_username", "p1"));
        return user;
    }
}
