package com.xll.redis.controller;

import com.xll.redis.bean.User;
import com.xll.redis.dto.UserDto;
import com.xll.redis.method.Insert;
import com.xll.redis.method.Update;
import com.xll.redis.param.AddOrUpdataUserParam;
import com.xll.redis.param.GetByNameParam;
import com.xll.redis.result.Result;
import com.xll.redis.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "根据id获取user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @RequestMapping("getuser")
    public Result<UserDto> getUser(@RequestParam(name = "id")Long id){
        return  Result.ok(userService.getUser(id));
    }


    @ApiOperation(value = "添加USER", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    @PostMapping("/addUser")
    public Result<String> addUser(@RequestBody @Validated(Insert.class) AddOrUpdataUserParam param, BindingResult BindingResult){
        userService.addUser(param);
        return Result.ok();
    }

    @PostMapping("/updateuser")
    @ResponseBody
    @ApiOperation(value = "修改USER", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    public Result<String> updateUser(@RequestBody@Validated(Update.class)AddOrUpdataUserParam param, BindingResult bindingResult){
        userService.updateUser(param);
        return Result.ok();
    }

    @ApiOperation(value = "删除USER", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "DELETE")
    @DeleteMapping("deluser")
    public Result<String> delUser(@RequestParam(required = false,name = "id") Long id){
        userService.deleteUser(id);
        return Result.ok();
    }

    @RequestMapping("getbyname")
    @ApiOperation(value = "根据name获取USER", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    public Result<List<User>> getByName(@RequestParam(defaultValue = "0")int currentPage, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "")String name, ServletRequest request1,HttpServletRequest request2){
        GetByNameParam param = new GetByNameParam(currentPage,pageSize,name);
        Result<List<User>> result = userService.getByName(param);
        result.setDesc("访问服务器端口是:"+request1.getLocalPort()+" sessionId:"+request2.getSession().getId());
        return result;
    }
}
