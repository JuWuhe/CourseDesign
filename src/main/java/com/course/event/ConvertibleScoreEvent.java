package com.course.event;

import com.course.pojo.LoginUser;

public class ConvertibleScoreEvent extends BaseScoreEvent {
    private final int trueType;
    private final int score;
    public enum ConvertibleScore{
        FollowUp(6,3),
        ExtendedRecruitment(7,5),
        ResearchRecruitment(8,8);
        private final int trueType;
        private final int score;

        ConvertibleScore(int trueType, int score) {
            this.trueType = trueType;
            this.score = score;
        }

        public int getTrueType() {
            return trueType;
        }
    }
    public ConvertibleScoreEvent(LoginUser loginUser, ConvertibleScore convertibleScore) {
        super(loginUser);
        this.trueType =convertibleScore.trueType;
        this.score = convertibleScore.score;
    }

    @Override
    public int strategyType() {
        return 6;
    }

    public int getTrueType() {
        return trueType;
    }

    public int getScore() {
        return score;
    }
}
