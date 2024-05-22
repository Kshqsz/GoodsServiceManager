package cn.edu.usts.cs2024.service.impl;

import cn.edu.usts.cs2024.mapper.UserMapper;
import cn.edu.usts.cs2024.pojo.User;
import cn.edu.usts.cs2024.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.login(username, password);
        return user;
    }
}
