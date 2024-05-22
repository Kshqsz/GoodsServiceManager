package cn.edu.usts.cs2024.service;

import cn.edu.usts.cs2024.pojo.User;

public interface UserService {


    User login(String username, String password);
}
