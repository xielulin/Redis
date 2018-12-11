package com.xll.redis.result;

import lombok.Data;

/**
 * @author xielulin
 * @create 2018-11-18 15:37
 * @desc
 **/
@Data
public class Result<T> {
    private long page;  //第几页

    private long size;  //每页多少条

    private long count; //总数

    private long countPage; //一共多少页

    private T data;

    private String status;

    private String desc;

    public Result() {
    }

    public Result(String status) {
        this.status = status;
    }

    public Result(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public Result(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Result(String status, T data, String desc) {
        this.status = status;
        this.data = data;
        this.desc = desc;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>("SUCC", data);
    }

    public static <T> Result<T> ok() {
        return new Result<>("SUCC", null, null);
    }

    public static <T> Result<T> ok(String message) {
        return new Result<>("SUCC", null, message);
    }

    public static <T> Result<T> unAuthorized() {
        return new Result<>("UNAUTHORIZED", null, null);
    }

    public static <T> Result<T> ok(T data, String desc) {
        return new Result<>("SUCC", data, desc);
    }

    public static Result warn(String desc) {
        return new Result<>("FAIL", desc);
    }

    public static <T> Result<T> warn(T data, String desc) {
        return new Result<>("FAIL", data, desc);
    }
}
