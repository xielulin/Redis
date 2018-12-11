package com.xll.redis.service;

import com.xll.redis.bean.User;
import com.xll.redis.param.AddOrUpdataUserParam;
import org.springframework.cache.annotation.CachePut;

/**
 * @author xielulin
 * @create 2018-11-18 00:22
 * @desc User
 **/
public interface UserService {


    /**
     *@description 获取
     * @author xielulin
     * @date 2018/11/18
     * @param id
     * @return com.xll.redis.bean.User
     */
    User getUser(Long id);

    /**
     * @description 新增
     * @author xielulin
     * @date 2018/11/18
     * @param param
     * @return void
     */
     void addUser(AddOrUpdataUserParam param);


     /**
      *@description 修改
      * @author xielulin
      * @date 2018/12/2
      * @param param
      * @return User
      */
     User updateUser(AddOrUpdataUserParam param);
}
