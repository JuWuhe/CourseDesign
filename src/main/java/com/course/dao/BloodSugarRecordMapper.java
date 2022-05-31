package com.course.dao;

import com.course.pojo.BloodSugarRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BloodSugarRecordMapper {

    @Insert("insert into blood_sugar_record (user_id, record, timestamp) VALUES (#{userId},#{record},#{timestamp})")
    @Options(useGeneratedKeys = true)
    int insertIntoRecord(BloodSugarRecord bloodSugarRecord);

    //还能优化 不动了
    @Select("select count(*) from blood_sugar_record where user_id = #{userId};")
    int countByUserId(Integer userId);
}
