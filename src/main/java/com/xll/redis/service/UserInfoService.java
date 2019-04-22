package com.xll.redis.service;

import com.xll.redis.bean.UserInfo;
import com.xll.redis.dto.UserInfoDto;
import com.xll.redis.param.AddOrUpdataUserInfoParam;
import com.xll.redis.param.GetUserInfosParam;
import com.xll.redis.result.Result;

import java.util.List;

/**
 * @author xielulin
 * @create 2018-12-21 10:18
 * @desc UserInfoService
 **/
public interface UserInfoService {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);

    /**
     * @description 获取用户列表
     * @author xielulin
     * @date 2018/12/26
     * @param getUserInfosParam
     * @return
     */
    Result<List<UserInfoDto>> getUserList(GetUserInfosParam getUserInfosParam);

    /**
     * @description 添加用户
     * @author xielulin
     * @date 2018/12/28
     * @param param
     * @return void
     */
    UserInfo addUserInfo(AddOrUpdataUserInfoParam param);

    /**
     * @description 根据id获取UserInfo-
     * @author xielulin
     * @date 2018/12/29
     * @param id
     * @return com.xll.redis.dto.UserInfoDto
     */
    UserInfoDto getUserById(Integer id);
}
