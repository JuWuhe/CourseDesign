package com.course.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BfzMapper {
//    @Insert("insert into bfz_record (user_id, record, timestamp) VALUES (#{userId},#{record},#{timestamp})")
//    @Options(useGeneratedKeys = true)
//    int insertIntoRecord(BfzRecord bfzRecord);
//
//    @Select("select count(*) from bfz_record where user_id = #{userId} and (timestamp between #{start} and #{end})")
//    int countByUserId(int userId, long start, long end);
}
