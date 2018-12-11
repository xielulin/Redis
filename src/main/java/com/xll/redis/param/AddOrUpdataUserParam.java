package com.xll.redis.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xielulin
 * @create 2018-11-18 16:03
 * @desc 新增User请求参数类
 **/
@Data
public class AddOrUpdataUserParam {

    private Long id;

    @NotNull
    private int age;

    @NotNull
    private String name;

    @NotNull
    private String sex;

}
