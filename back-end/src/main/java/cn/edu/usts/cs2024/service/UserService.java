package cn.edu.usts.cs2024.service;

import cn.edu.usts.cs2024.pojo.RegisterRequest;
import cn.edu.usts.cs2024.pojo.User;

import java.util.List;

public interface UserService {


    User login(String username, String password);

    void register(RegisterRequest registerRequest);

    List<User> getUsers();

    void deleteUserById(Integer id);

    User getUserByUsername(String username);
}
