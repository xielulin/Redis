package com.xll.redis.controller;

import com.xll.redis.bean.User;
import com.xll.redis.param.AddOrUpdataUserParam;
import com.xll.redis.param.GetByNameParam;
import com.xll.redis.result.Result;
import com.xll.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("getuser")
    public Result<User> getUser(@RequestParam(name = "id")Long id){
        return  Result.ok(userService.getUser(id));
    }

    @RequestMapping("adduser")
    public Result<String> addUser(AddOrUpdataUserParam param,BindingResult bindingResult){
        userService.addUser(param);
        return Result.ok();
    }

    @RequestMapping("updateuser")
    public Result<String> updateUser(@Valid AddOrUpdataUserParam param, BindingResult bindingResult){
        userService.updateUser(param);
        return Result.ok();
    }

    @DeleteMapping("deluser")
    public Result<String> delUser(@RequestParam(required = false,name = "id") Long id,BindingResult bindingResult){
        userService.deleteUser(id);
        return Result.ok();
    }

    @RequestMapping("getbyname")
    public Result<List<User>> getByName(@RequestParam(defaultValue = "0")int currentPage,@RequestParam(defaultValue = "10") int pageSize,@RequestParam()String name){
        GetByNameParam param = new GetByNameParam();
        param.setName(name);
        param.setCurrentPage(currentPage);
        param.setPageSize(pageSize);
        return userService.getByName(param);
    }
}
