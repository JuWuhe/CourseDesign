package com.course.dao;

import com.course.pojo.ScoreRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScoreMapper {

    @Insert("insert into score_record (user_id, `count`, `timestamp`,type) value (#{userId},#{count},#{timestamp},#{type})")
    @Options(useGeneratedKeys = true)
    int insertRecord(ScoreRecord sr);

    @Select("select count(*) from score_record where user_id = #{userId} and type = #{type} and (timestamp between #{start} and #{end})")
    int count(int userId,int type,long start,long end);

    @Select("select sum(count) from score_record where user_id = #{userId} and (timestamp between #{start} and #{end})")
    int sumAllScore(int userId,long start,long end);

    int sum(@Param("userId") int userId,@Param("start") long start,@Param("end") long end,@Param("types") List<Integer> types);

    int insertRecords(@Param("records") List<ScoreRecord> scoreRecords);

}
