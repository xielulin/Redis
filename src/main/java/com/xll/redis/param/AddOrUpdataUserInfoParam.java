package com.xll.redis.param;

import com.xll.redis.method.Insert;
import com.xll.redis.method.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xielulin
 * @create 2018-12-28 13:56
 * @desc
 **/
@Data
public class AddOrUpdataUserInfoParam {
    @NotNull(message = "uid不能为空",groups = {Update.class})
    private Integer uid;
    @NotBlank(message = "账号不能为空",groups = {Update.class, Insert.class})
    private String username;//帐号
    @NotBlank(message = "昵称不能为空",groups = {Update.class,Insert.class})
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    @NotBlank(message = "密码不能为空",groups = {Update.class,Insert.class})
    private String password;
}
