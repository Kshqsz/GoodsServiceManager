package cn.edu.usts.cs2024.controller;


import cn.edu.usts.cs2024.pojo.LoginRequest;
import cn.edu.usts.cs2024.pojo.RegisterRequest;
import cn.edu.usts.cs2024.pojo.Result;
import cn.edu.usts.cs2024.pojo.User;
import cn.edu.usts.cs2024.service.UserService;
import cn.edu.usts.cs2024.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User u  = userService.login(username, password);
        if (u == null) {
            return Result.error("用户名或密码错误");
        } else {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", username);
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
    }
    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
            return Result.error("两次密码输入不一致");
        }
        userService.register(registerRequest);
        return Result.success();
    }

    @GetMapping("/users")
    public Result<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return Result.success(users);
    }

}
