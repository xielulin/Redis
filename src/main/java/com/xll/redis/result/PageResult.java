package com.xll.redis.result;

import lombok.Data;

/**
 * @author xielulin
 * @create 2018-12-18 17:25
 * @desc 带分页的result
 **/
@Data
public class PageResult extends Result{
    private long totalPage;  //一共多少页

    private long pageSize;  //每页多少条

    private long total; //总数

    private long currentPage; //当前第几页


}
