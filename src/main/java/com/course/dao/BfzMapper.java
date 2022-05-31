package com.course.dao;

import com.course.pojo.BfzRecord;
import com.course.pojo.BloodSugarRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BfzMapper {
    @Insert("insert into bfz_record (user_id, record, timestamp) VALUES (#{userId},#{record},#{timestamp})")
    @Options(useGeneratedKeys = true)
    int insertIntoRecord(BfzRecord bfzRecord);

    //还能优化 不动了
    @Select("select count(*) from bfz_record where user_id = #{userId} and (timestamp between #{start} and #{end})")
    int countByUserId(int userId,long start,long end);
}
