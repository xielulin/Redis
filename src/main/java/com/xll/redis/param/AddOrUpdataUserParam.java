package com.xll.redis.param;

import com.xll.redis.method.Insert;
import com.xll.redis.method.Update;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xielulin
 * @create 2018-11-18 16:03
 * @desc 新增User请求参数类
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrUpdataUserParam {

    @NotNull(message = "id不能为空",groups = {Update.class})
    private Long id;

    @NotNull(message = "age不能为空",groups = {Update.class,Insert.class})
    private Integer age;

    @NotBlank(message = "name不能为空",groups = {Update.class,Insert.class})
    private String name;

    @NotBlank(message = "sex不能为空",groups = {Update.class,Insert.class})
    private String sex;

}
