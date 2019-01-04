package com.xll.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xielulin
 * @create 2018-12-29 10:41
 * @desc
 **/
@Data
@AllArgsConstructor
public class UserDto {
    private Long id;

    private Integer age;

    private String name;

    private String sex;
}
