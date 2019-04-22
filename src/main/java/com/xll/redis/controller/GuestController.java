package com.xll.redis.controller;

import com.xll.redis.constants.Constant;
import com.xll.redis.dto.UserInfoDto;
import com.xll.redis.method.Insert;
import com.xll.redis.param.AddOrUpdataUserInfoParam;
import com.xll.redis.param.GetUserInfosParam;
import com.xll.redis.result.Result;
import com.xll.redis.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xielulin
 * @create 2018-12-21 10:24
 * @desc GuestController
 **/
@RequestMapping("/guest")
@Controller
public class GuestController {
    @Autowired
    UserInfoService userInfoService;


    /**
     * 注册用户
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    @ApiOperation(value = "注册用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    public Result<String> userInfoAdd(@Validated(Insert.class) AddOrUpdataUserInfoParam param, BindingResult bindingResult){
        userInfoService.addUserInfo(param);
        return Result.ok(Constant.ResultConstant.SUCCESS);
    }

    @ApiOperation(value = "注册页面", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    @RequestMapping("/index")
    public String register(){
        return "register";
    }

}
