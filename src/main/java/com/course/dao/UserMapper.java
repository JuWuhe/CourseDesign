package com.course.dao;

import com.course.pojo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from login_user where  username = #{username}")
    LoginUser selectByUsername(String username);

    @Update("update login_user set information = #{information} Where user_id = #{userId}")
    int updateInformation(int userId,String information);

}
