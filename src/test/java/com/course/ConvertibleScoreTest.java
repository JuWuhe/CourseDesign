package com.course;


import com.course.event.ConvertibleScoreEvent;
import com.course.pojo.LoginUser;
import com.course.service.*;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ConvertibleScoreTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BfzNoteService noteService;
    @Autowired
    UserService userService;
    @Autowired
    ResearchRecruitmentService researchRecruitmentService;
    LoginUser loginUser;
    @BeforeEach
    public void initEachUser(){
        //https://stackoverflow.com/questions/58657587/beforeall-and-transaction-are-not-working-changes-on-db-side-are-not-rollbac
        if (loginUser == null) {
            loginUser = creatTestUser();
        }
        setUser(loginUser);
    }
    @Autowired
    FollowUpService followUpService;
    @Test
    @Rollback
    public void testFollowUp(){
        followUpService.followUp();
        var list = getScoreRecord(jdbcTemplate, getUser().getUserId(), ConvertibleScoreEvent.ConvertibleScore.FollowUp.getTrueType());
        assertEquals(1, list.size(), "此时应只有一条记录");
        assertEquals(3,list.get(0),"完成门诊随访3分");

        followUpService.followUp();
        list = getScoreRecord(jdbcTemplate, getUser().getUserId(), ConvertibleScoreEvent.ConvertibleScore.FollowUp.getTrueType());
        assertEquals(2, list.size(), "此时应只有两条记录");
        assertEquals(List.of(3,3),list,"两次完成门诊随访6分");
    }

    @Test
    @Rollback
    public void testResearchRecruitment(){
        researchRecruitmentService.researchRecruitment();
        var list = getScoreRecord(jdbcTemplate, getUser().getUserId(), ConvertibleScoreEvent.ConvertibleScore.ResearchRecruitment.getTrueType());
        assertEquals(1, list.size(), "此时应只有一条记录");
        assertEquals(8,list.get(0),"参加科研招募8分");

        researchRecruitmentService.researchRecruitment();
        list = getScoreRecord(jdbcTemplate, getUser().getUserId(), ConvertibleScoreEvent.ConvertibleScore.ResearchRecruitment.getTrueType());
        assertEquals(2, list.size(), "此时应只有两条记录");
        assertEquals(List.of(8,8),list,"a.参加科研招募16分");
    }

    @Test
    @Rollback
    public void testExtendedRecruitment(){
        // TODO:写
    }


    private LoginUser creatTestUser() {
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        return userService.Login(new LoginUser("test_username", "p1"));
    }

}
