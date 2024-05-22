package cn.edu.usts.cs2024.mapper;

import cn.edu.usts.cs2024.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username} and password = #{password}")
    User login(@Param("username") String username, @Param("password") String password);
}
