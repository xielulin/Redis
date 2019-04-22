package com.xll.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xielulin
 * @create 2018-12-27 10:20
 * @desc UserInfoDto
 **/
@Data
@AllArgsConstructor
public class UserInfoDto implements Serializable {
    private Integer uid;
    private String username;//帐号
    private String name;
}
