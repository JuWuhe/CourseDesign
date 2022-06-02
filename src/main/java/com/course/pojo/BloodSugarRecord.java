package com.course.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class BloodSugarRecord {
    private Integer recordId;
    private Integer userId;
    private String record;
    private Long timestamp;

    public BloodSugarRecord() {
    }

    public BloodSugarRecord(Integer userId, String record) {
        this.userId = userId;
        this.record = record;
        timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
    }
}
