package com.xll.redis.service;

import com.xll.redis.bean.User;
import com.xll.redis.dto.UserDto;
import com.xll.redis.param.AddOrUpdataUserParam;
import com.xll.redis.param.GetByNameParam;
import com.xll.redis.result.Result;

import java.util.List;

/**
 * @author xielulin
 * @create 2018-11-18 00:22
 * @desc User
 **/
public interface UserService {


    /**
     * @description 获取
     * @author xielulin
     * @date 2018/11/18
     * @param id
     * @return com.xll.redis.bean.User
     */
    UserDto getUser(Long id);

    /**
     * @description 新增
     * @author xielulin
     * @date 2018/11/18
     * @param param
     * @return void
     */
     User addUser(AddOrUpdataUserParam param);


     /**
      * @description 修改
      * @author xielulin
      * @date 2018/12/2
      * @param param
      * @return User
      */
     User updateUser(AddOrUpdataUserParam param);

     /**
      *@description 删除
      * @author xielulin
      * @date 2018/12/12
      * @param id
      * @return void
      */
    void deleteUser(Long id);

    /**
     * @description 根据name获取user
     * @author xielulin
     * @date 2018/12/17
     * @param param
     * @return java.util.List<com.xll.redis.bean.User>
     */
    Result<List<User>> getByName(GetByNameParam param);
}
