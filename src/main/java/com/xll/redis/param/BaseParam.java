package com.xll.redis.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xielulin
 * @create 2018-12-17 17:16
 * @desc 分页入参
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseParam {

    private Integer currentPage;

    private Integer pageSize;

}
