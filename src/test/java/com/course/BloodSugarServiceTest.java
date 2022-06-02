package com.course;

import com.course.dao.BloodSugarRecordMapper;
import com.course.pojo.LoginUser;
import com.course.service.BloodSugarService;
import com.course.service.UserService;
import com.course.service.score.BloodSugarScoreStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.course.TestUtil.getScoreRecord;
import static com.course.TestUtil.setUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class BloodSugarServiceTest {
    @Autowired
    BloodSugarService bloodSugarService;
    @Autowired
    BloodSugarRecordMapper bloodSugarRecordMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    @Test
    @Rollback
    public void recordBloodSugarTest(){
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        LoginUser user  = userService.Login(new LoginUser("test_username", "p1"));
        setUser(user);
        for (int i = 0; i < 3; i++) {
            bloodSugarService.recordBloodSugar("");
        }
        assertEquals(3, bloodSugarRecordMapper.countByUserId(user.getUserId()),"应该有三个记录");
        List<Integer> list = getScoreRecord(jdbcTemplate, user.getUserId(), BloodSugarScoreStrategy.type);
        assertEquals(0, list.size(), "此时不应有记录");


        bloodSugarService.recordBloodSugar("");
        assertEquals(4, bloodSugarRecordMapper.countByUserId(user.getUserId()),"应该有四个记录");
        list = getScoreRecord(jdbcTemplate, user.getUserId(), BloodSugarScoreStrategy.type);
        assertEquals(1, list.size(), "此时应只有一条记录");
        assertEquals(1,list.get(0),"填写血糖记录大于 3，积 1 分");
    }
}
