package com.course;

import com.course.dao.BloodSugarRecordMapper;
import com.course.pojo.LoginUser;
import com.course.service.BloodSugarService;
import com.course.service.EvaluateReportService;
import com.course.service.UserService;
import com.course.service.score.EvaluateReportScoreStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.course.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class EvaluateReportTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    @Autowired
    EvaluateReportService evaluateReportService;
    @Autowired
    BloodSugarService bloodSugarService;
    @Autowired
    BloodSugarRecordMapper bloodSugarRecordMapper;

    LoginUser loginUser;


    @BeforeEach
    public void initEachUser(){
        //https://stackoverflow.com/questions/58657587/beforeall-and-transaction-are-not-working-changes-on-db-side-are-not-rollbac
        if (loginUser == null) {
            loginUser = creatTestUser();
        }
        setUser(loginUser);
    }

    @Test
    @Rollback
    public void withoutAnyThing(){
        assertThrows(RuntimeException.class, ()->{
           evaluateReportService.evaluateReport();
        });
    }

    @Test
    @Rollback
    public void withFillInformation(){
        assertThrows(RuntimeException.class, ()->{
            userService.fillInformation(" ");
            evaluateReportService.evaluateReport();
        });
    }

    @Test
    @Rollback
    public void all(){
        // 填写个人资料
        userService.fillInformation(" ");
        // 十条血糖记录
        for (int i = 0; i < 10; i++) {
            bloodSugarService.recordBloodSugar(" ");
        }
        int count = bloodSugarRecordMapper.countByUserId(getUser().getUserId());
        assertEquals(10,count,"应该有十条记录");

        assertNotNull(evaluateReportService.evaluateReport());
        List<Integer> list = getScoreRecord(jdbcTemplate, getUser().getUserId(), EvaluateReportScoreStrategy.type);
        assertEquals(1, list.size(), "此时应只有一条记录");
        assertEquals(2,list.get(0),"需要先填写好个人资料，血糖记录数填写大于等于 10 每次2分");

        assertNotNull(evaluateReportService.evaluateReport());
        list = getScoreRecord(jdbcTemplate, getUser().getUserId(), EvaluateReportScoreStrategy.type);
        assertEquals(2, list.size(), "此时应只有两条记录");
        assertEquals(List.of(2,2),list);

    }

    private LoginUser creatTestUser() {
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        return userService.Login(new LoginUser("test_username", "p1"));
    }



}
