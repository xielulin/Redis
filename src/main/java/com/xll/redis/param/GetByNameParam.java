package com.xll.redis.param;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xielulin
 * @create 2018-12-18 12:00
 * @desc
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetByNameParam extends BaseParam {
    private String name;

    public GetByNameParam(Integer currentPage, Integer pageSize, String name) {
        super(currentPage, pageSize);
        this.name = name;
    }
}
