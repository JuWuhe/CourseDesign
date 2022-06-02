package com.course.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BfzRecord {
    private Integer recordId;
    private Integer userId;
    private String record;
    private Long timestamp;

    public BfzRecord(Integer userId, String record) {
        this.userId = userId;
        this.record = record;
        this.timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
    }
}
