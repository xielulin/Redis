package com.xll.redis.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author xielulin
 * @create 2018-12-18 12:00
 * @desc
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByNameParam extends BaseParam {
    @NotBlank(message = "name不能为空")
    private String name;
}
