package com.xll.redis.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xielulin
 * @create 2018-12-26 17:38
 * @desc
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfosParam extends BaseParam{
    private String name;

    public GetUserInfosParam(Integer currentPage, Integer pageSize, String name) {
        super(currentPage, pageSize);
        this.name = name;
    }
}
