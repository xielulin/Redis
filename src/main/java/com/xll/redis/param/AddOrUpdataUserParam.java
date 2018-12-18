package com.xll.redis.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author xielulin
 * @create 2018-11-18 16:03
 * @desc 新增User请求参数类
 **/
@Data
public class AddOrUpdataUserParam {

    private Long id;

    @NotNull(message = "age不能为空")
    private Integer age;

    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank(message = "sex不能为空")
    private String sex;

}
