package cn.edu.usts.cs2024.mapper;

import cn.edu.usts.cs2024.pojo.RegisterRequest;
import cn.edu.usts.cs2024.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username} and password = #{password}")
    User login(@Param("username") String username, @Param("password") String password);

    @Insert("insert into users(username, password) values (#{username}, #{password})")
    void register(RegisterRequest registerRequest);

    @Select("select * from users")
    List<User> getUsers();

    @Delete("delete from users where id = #{id}")
    void deleteUserById(Integer id);

    @Select("select * from users where username = #{username}")
    User getUserByUsername(String username);
}
