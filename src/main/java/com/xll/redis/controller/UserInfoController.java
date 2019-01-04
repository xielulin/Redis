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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xielulin
 * @create 2018-12-21 10:24
 * @desc UserInfoController
 **/
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    @ApiOperation(value = "用户查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    public Result<List<UserInfoDto>> userInfo(@RequestParam(defaultValue = "0")int currentPage, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "")String name){
        GetUserInfosParam getUserInfosParam = new GetUserInfosParam(currentPage,pageSize,name);
        return userInfoService.getUserList(getUserInfosParam);
    }

    /**
     * 用户添加;
     * @return
     */
    @PostMapping("/userAdd")
    @ApiOperation(value = "用户添加", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "POST")
    public Result<String> userInfoAdd(@Validated(Insert.class) AddOrUpdataUserInfoParam param, BindingResult bindingResult){
        userInfoService.addUserInfo(param);
        return Result.ok(Constant.ResultConstant.SUCCESS);
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    @ApiOperation(value = "用户删除", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    public String userDel(){
        return "userInfoDel";
    }


    /**
     * 根据id获取用户;
     * @return
     */
    @RequestMapping("/getById")
    @RequiresPermissions("userInfo:view")//权限管理;
    @ApiOperation(value = "根据id获取用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, httpMethod = "GET")
    public Result<UserInfoDto> userDel(@RequestParam Integer id){
        UserInfoDto dto= userInfoService.getUserById(id);
        return Result.ok(dto);
    }

}
