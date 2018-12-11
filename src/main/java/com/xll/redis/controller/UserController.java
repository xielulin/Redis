package com.xll.redis.controller;

import com.xll.redis.bean.User;
import com.xll.redis.param.AddOrUpdataUserParam;
import com.xll.redis.result.Result;
import com.xll.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("getuser")
    public Result<User> getUser(@RequestParam(required = false,name = "id") Long id){
        return  Result.ok(userService.getUser(id));
    }

    @RequestMapping("adduser")
    public Result<String> addUser(AddOrUpdataUserParam param){
        userService.addUser(param);
        return Result.ok();
    }

    @RequestMapping("updateuser")
    public Result<String> updateUser(AddOrUpdataUserParam param){
        userService.updateUser(param);
        return Result.ok();
    }
}
