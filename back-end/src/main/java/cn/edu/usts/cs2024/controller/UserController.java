package cn.edu.usts.cs2024.controller;


import cn.edu.usts.cs2024.pojo.LoginRequest;
import cn.edu.usts.cs2024.pojo.RegisterRequest;
import cn.edu.usts.cs2024.pojo.Result;
import cn.edu.usts.cs2024.pojo.User;
import cn.edu.usts.cs2024.service.UserService;
import cn.edu.usts.cs2024.utils.JwtUtil;
import cn.edu.usts.cs2024.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
        User u = userService.getUserByUsername(registerRequest.getUsername());
        if (u != null) {
            return Result.error("用户名已存在");
        }
        userService.register(registerRequest);
        return Result.success();
    }
    @GetMapping("/users")
    public Result<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return Result.success(users);
    }

    @DeleteMapping("/deleteUser/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        if (!("admin".equals(username))) {
            return Result.error("你没有权限删除用户");
        }
        userService.deleteUserById(id);
        return Result.success();
    }
}
